package com.example.zaas.pocketbanker.utils;

import java.util.ArrayList;
import java.util.List;

import com.example.zaas.pocketbanker.models.local.BranchAtm;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by adugar on 3/27/16.
 */
public class AtmBranchUtils
{
    public static List<BranchAtm> getDummyBranchAtms()
    {
        List<BranchAtm> dummyBranchAtms = new ArrayList<>();
        BranchAtm branchAtm = new BranchAtm("Richmond Road", "1, Richmond Circle, Richmond Road", "Bangalore",
                BranchAtm.Type.BRANCH, new LatLng(12.939848, 77.5872505));
        dummyBranchAtms.add(branchAtm);
        branchAtm = new BranchAtm("Cubbonpet", "Cubbonpet Main Road, 199/1, 9th cross", "Bangalore",
                BranchAtm.Type.ATM, new LatLng(12.970534, 77.583551));
        dummyBranchAtms.add(branchAtm);
        branchAtm = new BranchAtm("Ejipura", "100 Ft Road, Ejipura", "Bangalore", BranchAtm.Type.ATM, new LatLng(
                12.938624, 77.631830));
        dummyBranchAtms.add(branchAtm);
        branchAtm = new BranchAtm("Adugodi", "Near Forum Mall, Adugodi", "Bangalore", BranchAtm.Type.ATM, new LatLng(
                12.939955, 77.614663));
        dummyBranchAtms.add(branchAtm);
        branchAtm = new BranchAtm("Lal Bagh", "Lal Bagh Road, Raja Ram Mohanroy Extension, Sudhama Nagar", "Bangalore",
                BranchAtm.Type.ATM, new LatLng(12.966250, 77.588004));
        dummyBranchAtms.add(branchAtm);
        branchAtm = new BranchAtm("Jayanagar", "Jayanagar 9th Block", "Bangalore", BranchAtm.Type.BRANCH, new LatLng(
                12.923837, 77.593396));
        dummyBranchAtms.add(branchAtm);
        branchAtm = new BranchAtm("HAL", "HAL Airport Rd, ISRO Colony, Domlur", "Bangalore", BranchAtm.Type.BRANCH,
                new LatLng(12.966355, 77.644921));
        dummyBranchAtms.add(branchAtm);
        branchAtm = new BranchAtm("Chickpet", "OTC Road, Chickpet", "Bangalore", BranchAtm.Type.BRANCH, new LatLng(
                12.977261, 77.574992));
        dummyBranchAtms.add(branchAtm);

        return dummyBranchAtms;
    }
}
