package org.custom.utils;

import java.math.BigDecimal;
import java.util.Date;

public class ABean {
    
    private BigDecimal aaValue;
    private String bbValue;
    private Date ccDate;
    

    public BigDecimal getAaValue() {
        return aaValue;
    }

    public void setAaValue(BigDecimal aaValue) {
        this.aaValue = aaValue;
    }

    public String getBbValue() {
        return bbValue;
    }

    public void setBbValue(String bbValue) {
        this.bbValue = bbValue;
    }

    public Date getCcDate() {
        return ccDate;
    }

    public void setCcDate(Date ccDate) {
        this.ccDate = ccDate;
    }

}
