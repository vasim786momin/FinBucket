package com.fin.kekanepc.finbucket.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fin.kekanepc.finbucket.AppHome;
import com.fin.kekanepc.finbucket.Data.HomeLoanData;
import com.fin.kekanepc.finbucket.Interface.ItemClickListener;
import com.fin.kekanepc.finbucket.R;

import java.util.List;

/**
 * Created by kekane pc on 27/04/2016.
 */
public class HomeLoanListAdapter extends RecyclerView.Adapter<HomeLoanListAdapter.ViewHolder>
{

    private List<HomeLoanData> homeLoanDataList;
    Context context;

    public HomeLoanListAdapter(Context context,List<HomeLoanData> homedataoneList )
    {
        super();
        this.context = context;
        this.homeLoanDataList = homedataoneList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.homeloanlistitem, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final HomeLoanData item = homeLoanDataList.get(position);

        final String bankname = item.getBankname();
        final String roi      = item.getRoi();
        final String emi      = item.getEmi();
        final String loanAmt  = item.getLoanAmount();

        System.out.println(bankname + "" + emi + "" + roi + "" + loanAmt);

        if(bankname.equals("HDFC"))
        {
            viewHolder.BankLogo.setImageResource(R.drawable.hdfc);
        }
        if(bankname.equals("INDUSIND"))
        {
            viewHolder.BankLogo.setImageResource(R.drawable.indus);
        }
        if(bankname.equals("Axis"))
        {
            viewHolder.BankLogo.setImageResource(R.drawable.axis);
        }
        if(bankname.equals("Kotak"))
        {
            viewHolder.BankLogo.setImageResource(R.drawable.kotak);
        }
        if(bankname.equals("ICICI"))
        {
            viewHolder.BankLogo.setImageResource(R.drawable.icici);
        }
        if(bankname.equals("LIC-HFL"))
        {
            viewHolder.BankLogo.setImageResource(R.drawable.lichfl);
        }
        if(bankname.equals("IDBI"))
        {
            viewHolder.BankLogo.setImageResource(R.drawable.idbi);
        }
        if(bankname.equals("SBH"))
        {
            viewHolder.BankLogo.setImageResource(R.drawable.sbh);
        }
        if(bankname.equals("Central bank"))
        {
            viewHolder.BankLogo.setImageResource(R.drawable.central);
        }
        if(bankname.equals("SBI"))
        {
            viewHolder.BankLogo.setImageResource(R.drawable.sbi);
        }
      //  int mobileres = Icons[position];
     //   viewHolder.imgThumbnail.setImageResource(mobileres);
        viewHolder.BankName.setText(bankname);
        viewHolder.Roi.setText(roi);
        viewHolder.EMi.setText(emi+" ₹");
        viewHolder.LoanAmount.setText(loanAmt+" ₹");

    viewHolder.setClickListener(new ItemClickListener() {
        @Override
        public void onClick(View view, int position, boolean isLongClick) {
            if (isLongClick) {
                Toast.makeText(context, " (Long click)", Toast.LENGTH_SHORT).show();
                // context.startActivity(new Intent(context, AvailableTables.class));
            } else {

                //Toast.makeText(context, "#tableid" + tableid + "#hotelid" + hotelid +"#date" + date +"#time" + time ,Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(context, AppHome.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(myIntent);
            }
        }
    });
    }

    @Override
    public int getItemCount() {
        return homeLoanDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public ImageView BankLogo;
        public TextView BankName;
        public TextView EMi;
        public TextView Roi;
        public TextView LoanAmount;
        private ItemClickListener clickListener;

        public ViewHolder(View itemView) {
            super(itemView);

            BankLogo            = (ImageView) itemView.findViewById(R.id.logo);
            BankName            = (TextView)  itemView.findViewById(R.id.bankname);
            Roi                 = (TextView)  itemView.findViewById(R.id.roi);
            EMi                 = (TextView)  itemView.findViewById(R.id.emi);
            LoanAmount          = (TextView)  itemView.findViewById(R.id.amt);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

     public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View view)
        {
          clickListener.onClick(view, getPosition(), false);
        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onClick(view, getPosition(), true);
            return true;
        }
    }

}