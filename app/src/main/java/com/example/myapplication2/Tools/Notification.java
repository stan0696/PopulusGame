package com.example.myapplication2.Tools;

public class Notification {
    private int Notifyid;
    private String NotifySender;
    private String NotifyTittle;
    private String Notifymessage;
    public  Notification(String NotifySender){
        this.NotifySender=NotifySender;
    }

    public String getNotifySender() {
        return NotifySender;
    }

    public void setNotifySender(String notifySender) {
        NotifySender = notifySender;
    }

    public String getNotifyTittle() {
        return NotifyTittle;
    }

    public void setNotifyTittle(String notifyTittle) {
        NotifyTittle = notifyTittle;
    }

    public String getNotifymessage() {
        return Notifymessage;
    }

    public void setNotifymessage(String notifymessage) {
        Notifymessage = notifymessage;
    }

    public int getNotifyid() {
        return Notifyid;
    }

    public void setNotifyid(int notifyid) {
        Notifyid = notifyid;
    }
}
