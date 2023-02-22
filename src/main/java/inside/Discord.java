package inside;

public class Discord {
    public static void sendChat(InterServerCommunicator.MessageSendEvent event){

    }

    public static void sendGates(InterServerCommunicator.GatesEvent event){

    }

    public static void sendAdminReq(InterServerCommunicator.AdminReqEvent event){

    }

    public static void postMessage(String author, String message, int port){
        InterServerCommunicator.safeSend(new InterServerCommunicator.IncomeMessageEvent(author,message,port));
    }

    public static void adminResult(String author, boolean give, int port){
        InterServerCommunicator.safeSend(new InterServerCommunicator.AdminResEvent(author,give,port));
    }

    public static int getPort(long id){
        for(HostData server : LightweightHub.config.servers){
            if(server.discord == id){
                return server.port;
            }
        }
        return -1;
    }

    public static long getDiscord(int port){
        for(HostData server : LightweightHub.config.servers){
            if(server.port == port){
                return server.discord;
            }
        }
        return -1;
    }
}
