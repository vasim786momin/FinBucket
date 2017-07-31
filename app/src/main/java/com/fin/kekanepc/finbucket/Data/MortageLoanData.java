package com.fin.kekanepc.finbucket.Data;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by kekane pc on 05/05/2016.
 */
public class MortageLoanData  extends OutputStream
{


    String bankname,roi,emi,loanAmount;

    public MortageLoanData()
    {

    }

    public MortageLoanData(String bankname,String roi,String emi,String loanAmount)
    {
        this.bankname=bankname;
        this.roi=roi;
        this.emi=emi;
        this.loanAmount=loanAmount;

    }



    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getEmi() {
        return emi;
    }

    public void setEmi(String emi) {
        this.emi = emi;
    }

    public String getRoi() {
        return roi;
    }

    public void setRoi(String roi) {
        this.roi = roi;
    }

    @Override
    public void write(int oneByte) throws IOException {

    }
}
