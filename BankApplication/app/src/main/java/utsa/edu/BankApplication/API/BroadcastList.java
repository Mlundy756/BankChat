package utsa.edu.BankApplication.API;

public class BroadcastList {

    public Broadcast[] broadcasts;

    public class Broadcast {
        public String _id;
        public String createdAt;
        public String title;
        public String text;
    }
}
