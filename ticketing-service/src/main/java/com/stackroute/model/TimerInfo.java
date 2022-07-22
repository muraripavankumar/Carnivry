package com.stackroute.model;

public class TimerInfo {
    private int totalFireCount;
    private boolean runForever;
    private long RepeatIntervalMs;
    private long initialOffsetMs;
    private String CallbackData;
    private int SeatId;

    public int getTotalFireCount() {
        return totalFireCount;
    }

    public void setTotalFireCount(int totalFireCount) {
        this.totalFireCount = totalFireCount;
    }

    public boolean isRunForever() {
        return runForever;
    }

    public void setRunForever(boolean runForever) {
        this.runForever = runForever;
    }

    public long getRepeatIntervalMs() {
        return RepeatIntervalMs;
    }

    public void setRepeatIntervalMs(long repeatIntervalMs) {
        RepeatIntervalMs = repeatIntervalMs;
    }

    public long getInitialOffsetMs() {
        return initialOffsetMs;
    }

    public void setInitialOffsetMs(long initialOffsetMs) {
        this.initialOffsetMs = initialOffsetMs;
    }

    public String getCallbackData() {
        return CallbackData;
    }

    public void setCallbackData(String callbackData) {
        CallbackData = callbackData;
    }

    public int getSeatId() {
        return SeatId;
    }

    public void setSeatId(int seatId) {
        SeatId = seatId;
    }
}
