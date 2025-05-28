package com.example.bpproject;

import java.util.ArrayList;
import java.util.List;

public class KanbanCard {
    private String clientName;
    private String date;
    private List<Boolean> stepsCompleted;

    public KanbanCard(String clientName, String date) {
        this.clientName = clientName;
        this.date = date;
        this.stepsCompleted = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            stepsCompleted.add(false);
        }
    }

    public String getClientName() {
        return clientName;
    }

    public String getDate() {
        return date;
    }

    public List<Boolean> getStepsCompleted() {
        return stepsCompleted;
    }

    public void setStepCompleted(int stepIndex, boolean completed) {
        if (stepIndex >= 0 && stepIndex < stepsCompleted.size()) {
            stepsCompleted.set(stepIndex, completed);
        }
    }

    public int getCompletedCount() {
        int count = 0;
        for (Boolean completed : stepsCompleted) {
            if (completed) count++;
        }
        return count;
    }

    public int getTotalSteps() {
        return stepsCompleted.size();
    }

    public int getProgressPercent() {
        if (stepsCompleted.isEmpty()) return 0;
        return (getCompletedCount() * 100) / getTotalSteps();
    }
}
