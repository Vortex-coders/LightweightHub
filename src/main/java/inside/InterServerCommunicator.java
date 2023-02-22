package inside;

import arc.Events;
import arc.util.Log;
import fr.xpdustry.javelin.JavelinEvent;
import fr.xpdustry.javelin.JavelinPlugin;
import fr.xpdustry.javelin.JavelinSocket;
import mindustry.game.EventType;
import mindustry.net.Administration;

public class InterServerCommunicator {
    public static JavelinSocket socket;

    public void init() {
        socket = JavelinPlugin.getJavelinSocket();

        Events.on(EventType.ServerLoadEvent.class, e -> {
            socket = JavelinPlugin.getJavelinSocket();

            socket.subscribe(GatesEvent.class, Discord::sendGates);

            socket.subscribe(MessageSendEvent.class, Discord::sendChat);

            socket.subscribe(AdminReqEvent.class, Discord::sendAdminReq);
        });
    }

    public static void safeSend(JavelinEvent event) {
        if (socket.getStatus() == JavelinSocket.Status.OPEN) {
            socket.sendEvent(event);
        } else {
            Log.debug("|>| Socket error |<| Status " + socket.getStatus().name());
            socket = JavelinPlugin.getJavelinSocket();
            Log.debug("|>| Socket recon |<| Status " + socket.getStatus().name());
            socket.sendEvent(event);
        }
    }

    public static final class MessageSendEvent implements JavelinEvent {
        private final String authorName;
        private final String message;
        private final int port;

        public MessageSendEvent(String author, String msg) {
            this.authorName = author;
            this.message = msg;
            this.port = Administration.Config.port.num();
        }
    }

    public static final class IncomeMessageEvent implements JavelinEvent {
        private final String authorName;
        private final String message;
        private final int port;

        public IncomeMessageEvent(String author, String msg, int port) {
            this.authorName = author;
            this.message = msg;
            this.port = port;
        }

        public int getPort() {
            return this.port;
        }

        public String getAuthorName() {
            return authorName;
        }

        public String getMessage() {
            return message;
        }
    }


    public static final class GatesEvent implements JavelinEvent {
        private final String user;
        private final boolean join;
        private final int port;

        public GatesEvent(String author, boolean join) {
            this.user = author;
            this.join = join;
            this.port = Administration.Config.port.num();
        }
    }

    public static final class AdminReqEvent implements JavelinEvent {
        private final String user;
        private final String uuid;
        private final int port;

        public AdminReqEvent(String author, String uuid) {
            this.user = author;
            this.uuid = uuid;
            this.port = Administration.Config.port.num();
        }
    }

    public static final class AdminResEvent implements JavelinEvent {
        private final String uuid;
        private final boolean confirm;
        private final int port;

        public AdminResEvent(String uuid,boolean confirm, int port) {
            this.uuid = uuid;
            this.confirm = confirm;
            this.port = port;
        }
    }
}