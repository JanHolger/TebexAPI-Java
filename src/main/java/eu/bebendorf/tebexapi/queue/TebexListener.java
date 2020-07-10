package eu.bebendorf.tebexapi.queue;

import eu.bebendorf.tebexapi.model.TebexCommand;

public interface TebexListener {

    boolean onOfflineCommand(TebexCommand command);
    boolean onOnlineCommand(TebexCommand command);

}
