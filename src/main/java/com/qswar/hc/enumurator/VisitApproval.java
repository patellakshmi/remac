package com.qswar.hc.enumurator;

public enum VisitApproval {
    PENDING("PENDING"), APPROVED("APPROVED"), REJECTED("REJECTED");
    private String value;
    private VisitApproval(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }

    public VisitApproval approval(String approvalValue) {
        for (VisitApproval approval : VisitApproval.values()) {
            if (approval.value.equalsIgnoreCase(approvalValue)) {
                return approval;
            }
        }
        return null;
    }

}
