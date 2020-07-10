package eu.bebendorf.tebexapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import eu.bebendorf.tebexapi.model.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class TebexAPI {

    private static final String BASE_URL = "https://plugin.tebex.io";
    private static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    private String secret;
    private long nextQueueCheck;
    private TebexPlayer[] lastQueueCheck;
    private int onlineInterval = 0;

    public TebexAPI(String secret){
        this.secret = secret;
    }

    public int getOnlineInterval(){
        return onlineInterval;
    }

    private HttpResponse http(String method, String url, String body){
        HttpURLConnection conn = null;
        try{
            URL theUrl = new URL(BASE_URL+url);
            conn = (HttpURLConnection) theUrl.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(5000);
            conn.setRequestMethod(method);
            conn.setDoInput(true);
            conn.setRequestProperty("X-Tebex-Secret", secret);
            conn.setRequestProperty("Content-Type", "application/json");
            if(body!=null){
                conn.setDoOutput(true);
                OutputStream os = conn.getOutputStream();
                os.write(body.getBytes(StandardCharsets.UTF_8));
                os.flush();
                os.close();
            }
            int responseCode = conn.getResponseCode();
            if(responseCode>299 || responseCode < 200){
                return new HttpResponse(responseCode, new String(readAll(conn.getErrorStream()), StandardCharsets.UTF_8));
            }else{
                return new HttpResponse(responseCode, new String(readAll(conn.getInputStream()), StandardCharsets.UTF_8));
            }
        }catch(Exception e){
            try {
                return new HttpResponse(0, new String(readAll(conn.getErrorStream()), StandardCharsets.UTF_8));
            }catch(IOException | NullPointerException ex){}
        }
        return new HttpResponse(0, "");
    }

    private static byte[] readAll(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int r = 0;
        while (r != -1){
            r = is.read(data);
            if(r != -1)
                baos.write(data, 0, r);
        }
        is.close();
        return baos.toByteArray();
    }

    public TebexInformation getInformation() throws TebexException {
        return http("GET", "/information", null).getBodyOrError(TebexInformation.class);
    }

    public TebexPlayerLookup lookupPlayer(String player) throws TebexException {
        return http("GET", "/user/"+player, null).getBodyOrError(TebexPlayerLookup.class);
    }

    public TebexPlayer[] getQueue() throws TebexException {
        if(System.currentTimeMillis()<nextQueueCheck)
            return lastQueueCheck;
        CommandQueueResponse response = http("GET", "/queue", null).getBodyOrError(CommandQueueResponse.class);
        System.out.println(response.meta.next_check);
        nextQueueCheck = System.currentTimeMillis()+(response.meta.next_check*1000);
        onlineInterval = response.meta.next_check;
        lastQueueCheck = response.players;
        return response.players;
    }

    public TebexCommand[] getOfflineCommands() throws TebexException {
        return http("GET", "/queue/offline-commands", null).getBodyOrError(OfflineCommandQueueResponse.class).commands;
    }

    public TebexCommand[] getOnlineCommands(TebexPlayer player) throws TebexException {
        return getOnlineCommands(player);
    }

    public TebexCommand[] getOnlineCommands(int playerId) throws TebexException {
        return http("GET", "/queue/online-commands/"+playerId, null).getBodyOrError(TebexCommand[].class);
    }

    public void deleteCommands(TebexCommand... commands) throws TebexException {
        int[] ids = new int[commands.length];
        for(int i=0; i<ids.length; i++)
            ids[i] = commands[i].id;
        deleteCommands(ids);
    }

    public void deleteCommands(int... ids) throws TebexException {
        http("DELETE", "/queue", GSON.toJson(new DeleteRequest(ids))).orError();
    }

    private static class CommandQueueResponse {
        public Meta meta;
        public TebexPlayer[] players;
        private static class Meta {
            public boolean execute_offline;
            public int next_check;
            public boolean more;
        }
    }

    private static class OfflineCommandQueueResponse {
        public Meta meta;
        public TebexCommand[] commands;
        private static class Meta {
            public boolean limited;
        }
    }

    private static class DeleteRequest {
        public int[] ids;
        public DeleteRequest(int[] ids){
            this.ids = ids;
        }
    }

    private static class HttpResponse {
        private int code;
        private String body;
        public HttpResponse(int code, String body){
            this.code = code;
            this.body = body;
        }
        public boolean isSuccess(){
            return code > 199 && code < 300;
        }
        public <T> T getBody(Class<T> type){
            return GSON.fromJson(body, type);
        }
        public <T> T getBodyOrError(Class<T> type) throws TebexException {
            orError();
            return getBody(type);
        }
        public void orError() throws TebexException {
            if(!isSuccess())
                throw new TebexException(getBody(TebexError.class).errorMessage);
        }
    }

}
