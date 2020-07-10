package eu.bebendorf.tebexapi.queue;

import eu.bebendorf.tebexapi.TebexAPI;
import eu.bebendorf.tebexapi.TebexException;
import eu.bebendorf.tebexapi.model.TebexCommand;
import eu.bebendorf.tebexapi.model.TebexPlayer;

import java.util.ArrayList;
import java.util.List;

public class TebexQueue implements Runnable {

    private TebexAPI tebexAPI;
    private TebexListener listener;
    private long offlineInterval = 10000;
    private long onlineInterval = 90000;
    private long nextOffline = 0;
    private long nextOnline = 0;
    private boolean onlineEnabled = false;

    public TebexQueue(TebexAPI tebexAPI, TebexListener listener){
        this.tebexAPI = tebexAPI;
        this.listener = listener;
    }

    public void setOfflineInterval(long offlineInterval){
        this.offlineInterval = offlineInterval;
    }

    public void setOnlineEnabled(boolean onlineEnabled){
        this.onlineEnabled = onlineEnabled;
    }

    public void run(){
        while (true){
            long now = System.currentTimeMillis();
            if(nextOffline < now){
                try {
                    TebexCommand[] commands = tebexAPI.getOfflineCommands();
                    List<TebexCommand> deletable = new ArrayList<>();
                    for(TebexCommand command : commands){
                        try {
                            if(listener.onOfflineCommand(command))
                                deletable.add(command);
                        }catch (Exception ex){}
                    }
                    if(deletable.size() > 0)
                        tebexAPI.deleteCommands(deletable.toArray(new TebexCommand[deletable.size()]));
                } catch (TebexException e) {
                    e.printStackTrace();
                }
                nextOffline = now + offlineInterval;
            }
            if(onlineEnabled && nextOnline < now){
                try {
                    List<TebexCommand> deletable = new ArrayList<>();
                    for(TebexPlayer player : tebexAPI.getQueue()){
                        TebexCommand[] commands = tebexAPI.getOnlineCommands(player);
                        for(TebexCommand command : commands){
                            try {
                                if(listener.onOnlineCommand(command))
                                    deletable.add(command);
                            }catch (Exception ex){}
                        }
                    }
                    onlineInterval = tebexAPI.getOnlineInterval()*1000;
                    if(deletable.size() > 0)
                        tebexAPI.deleteCommands(deletable.toArray(new TebexCommand[deletable.size()]));
                } catch (TebexException e) {
                    e.printStackTrace();
                }
                nextOnline = now + onlineInterval;
            }
            try {
                Thread.sleep(Math.min(nextOffline, nextOnline)-now+1);
            } catch (InterruptedException e) {
            }
        }
    }

}
