package ru.zzbo.concretemobile.protocol.profinet.commands;

import static ru.zzbo.concretemobile.utils.Constants.preDosageWaterPercent;
import static ru.zzbo.concretemobile.utils.Constants.retrieval;
import static ru.zzbo.concretemobile.utils.Constants.tagListManual;
import static ru.zzbo.concretemobile.utils.Constants.lockStateRequests;
import static ru.zzbo.concretemobile.utils.Constants.zoneMixingEnd;

import ru.zzbo.concretemobile.models.Recepie;
import ru.zzbo.concretemobile.protocol.profinet.models.Tag;
import ru.zzbo.concretemobile.utils.MathUtils;

public class SetRecipe {

    public boolean sendRecipeToPLC(Recepie recepie) {
        try {
            Tag buncker11Tag = tagListManual.get(35);
            Tag buncker12Tag = tagListManual.get(36);
            Tag buncker21Tag = tagListManual.get(37);
            Tag buncker22Tag = tagListManual.get(38);
            Tag buncker31Tag = tagListManual.get(39);
            Tag buncker32Tag = tagListManual.get(40);
            Tag buncker41Tag = tagListManual.get(41);
            Tag buncker42Tag = tagListManual.get(42);

            Tag waterTag = tagListManual.get(43);
            Tag water2Tag = tagListManual.get(131);
            Tag chemy1Tag = tagListManual.get(44);
            Tag chemy2Tag = tagListManual.get(45);
            Tag chemy3Tag = tagListManual.get(46);
            Tag silos1Tag = tagListManual.get(47);
            Tag silos2Tag = tagListManual.get(48);

            Tag shortageBuncker11Tag = tagListManual.get(50);
            Tag shortageBuncker12Tag = tagListManual.get(51);
            Tag shortageBuncker21Tag = tagListManual.get(52);
            Tag shortageBuncker22Tag = tagListManual.get(53);
            Tag shortageBuncker31Tag = tagListManual.get(54);
            Tag shortageBuncker32Tag = tagListManual.get(55);
            Tag shortageBuncker41Tag = tagListManual.get(56);
            Tag shortageBuncker42Tag = tagListManual.get(57);

            Tag shortageWater1 = tagListManual.get(58);
            Tag shortageWater2 = tagListManual.get(132);
            Tag shortageChemy1 = tagListManual.get(59);
            Tag shortageChemy2 = tagListManual.get(60);
            Tag shortageSilos1 = tagListManual.get(61);
            Tag shortageSilos2 = tagListManual.get(86);

            Tag timeMixTag = tagListManual.get(65);

            Tag humidity11Tag = tagListManual.get(123);
            Tag humidity12Tag = tagListManual.get(124);
            Tag humidity21Tag = tagListManual.get(125);
            Tag humidity22Tag = tagListManual.get(126);
            Tag humidity31Tag = tagListManual.get(127);
            Tag humidity32Tag = tagListManual.get(128);
            Tag humidity41Tag = tagListManual.get(129);
            Tag humidity42Tag = tagListManual.get(130);

            Tag fibraTag = tagListManual.get(122);
            Tag dDryChTag = tagListManual.get(173);
            Tag dwplRecepie = tagListManual.get(186);

            buncker11Tag.setRealValueIf(recepie.getBunckerRecepie11());
            shortageBuncker11Tag.setRealValueIf(recepie.getBunckerShortage11());
            buncker12Tag.setRealValueIf(recepie.getBunckerRecepie12());
            shortageBuncker12Tag.setRealValueIf(recepie.getBunckerShortage12());

            buncker21Tag.setRealValueIf(recepie.getBunckerRecepie21());
            shortageBuncker21Tag.setRealValueIf(recepie.getBunckerShortage21());
            buncker22Tag.setRealValueIf(recepie.getBunckerRecepie22());
            shortageBuncker22Tag.setRealValueIf(recepie.getBunckerShortage22());

            buncker31Tag.setRealValueIf(recepie.getBunckerRecepie31());
            shortageBuncker31Tag.setRealValueIf(recepie.getBunckerShortage31());
            buncker32Tag.setRealValueIf(recepie.getBunckerRecepie32());
            shortageBuncker32Tag.setRealValueIf(recepie.getBunckerShortage32());

            buncker41Tag.setRealValueIf(recepie.getBunckerRecepie41());
            shortageBuncker41Tag.setRealValueIf(recepie.getBunckerShortage41());
            buncker42Tag.setRealValueIf(recepie.getBunckerRecepie42());
            shortageBuncker42Tag.setRealValueIf(recepie.getBunckerShortage42());

            waterTag.setRealValueIf(recepie.getWater1Recepie());
            water2Tag.setRealValueIf(recepie.getWater2Recepie());
            chemy1Tag.setRealValueIf(recepie.getChemyRecepie1());
            chemy2Tag.setRealValueIf(recepie.getChemy2Recepie());

            silos1Tag.setRealValueIf(recepie.getSilosRecepie1());
            silos2Tag.setRealValueIf(recepie.getSilosRecepie2());

            shortageWater1.setRealValueIf(recepie.getWater1Shortage());
            shortageWater2.setRealValueIf(recepie.getWater2Shortage());
            shortageChemy1.setRealValueIf(recepie.getChemyShortage1());
            shortageChemy2.setRealValueIf(recepie.getChemyShortage2());
            shortageSilos1.setRealValueIf(recepie.getSilosShortage1());
            shortageSilos2.setRealValueIf(recepie.getSilosShortage2());

            humidity11Tag.setRealValueIf(recepie.getHumidity11());
            humidity12Tag.setRealValueIf(recepie.getHumidity12());
            humidity21Tag.setRealValueIf(recepie.getHumidity21());
            humidity22Tag.setRealValueIf(recepie.getHumidity22());
            humidity31Tag.setRealValueIf(recepie.getHumidity31());
            humidity32Tag.setRealValueIf(recepie.getHumidity32());
            humidity41Tag.setRealValueIf(recepie.getHumidity41());
            humidity42Tag.setRealValueIf(recepie.getHumidity42());

            fibraTag.setRealValueIf(recepie.getFibra());
            recepie.setdDryCh(recepie.getdDryCh());
            dDryChTag.setRealValueIf(recepie.getdDryCh());
            dwplRecepie.setRealValueIf(recepie.getPathToHumidity());

            //блокировка
            lockStateRequests = true;

            if (recepie.getBunckerRecepie11() == 0 && retrieval.getHopper11RecipeValue() == 0) System.err.println("pie11 empty");
            else new CommandDispatcher(buncker11Tag).writeSingleRegisterWithoutLock();

            if (recepie.getBunckerShortage11() == 0 && retrieval.getShortageHopper11FactValue() == 0) System.err.println("short11 empty");
            else new CommandDispatcher(shortageBuncker11Tag).writeSingleRegisterWithoutLock();
            if (recepie.getBunckerRecepie12() == 0 && retrieval.getHopper12RecipeValue() == 0) System.err.println("pie12 empty");
            else new CommandDispatcher(buncker12Tag).writeSingleRegisterWithoutLock();

            if (recepie.getBunckerShortage12() == 0 && retrieval.getShortageHopper12FactValue() == 0) System.err.println("short12 empty");
            else new CommandDispatcher(shortageBuncker12Tag).writeSingleRegisterWithoutLock();

            if (recepie.getBunckerRecepie21() == 0 && retrieval.getHopper21RecipeValue() == 0) System.err.println("pie21 empty");
            else new CommandDispatcher(buncker21Tag).writeSingleRegisterWithoutLock();

            if (recepie.getBunckerShortage21() == 0  && retrieval.getShortageHopper21FactValue() == 0) System.err.println("short21 empty");
            else new CommandDispatcher(shortageBuncker21Tag).writeSingleRegisterWithoutLock();

            if (recepie.getBunckerRecepie22() == 0 && retrieval.getHopper22RecipeValue() == 0) System.err.println("pie22 empty");
            else new CommandDispatcher(buncker22Tag).writeSingleRegisterWithoutLock();

            if (recepie.getBunckerShortage22() == 0 && retrieval.getShortageHopper22FactValue() == 0) System.err.println("short22 empty");
            else new CommandDispatcher(shortageBuncker22Tag).writeSingleRegisterWithoutLock();

            if (recepie.getBunckerRecepie31() == 0 && retrieval.getHopper31RecipeValue() == 0) System.err.println("pie31 empty");
            else new CommandDispatcher(buncker31Tag).writeSingleRegisterWithoutLock();

            if (recepie.getBunckerShortage31() == 0 && retrieval.getShortageHopper31FactValue() == 0) System.err.println("short31 empty");
            else new CommandDispatcher(shortageBuncker31Tag).writeSingleRegisterWithoutLock();

            if (recepie.getBunckerRecepie32() == 0 && retrieval.getHopper32RecipeValue() == 0) System.err.println("pie32 empty");
            else new CommandDispatcher(buncker32Tag).writeSingleRegisterWithoutLock();

            if (recepie.getBunckerShortage32() == 0 && retrieval.getShortageHopper32FactValue() == 0) System.err.println("short32 empty");
            else new CommandDispatcher(shortageBuncker32Tag).writeSingleRegisterWithoutLock();

            if (recepie.getBunckerRecepie41() == 0 && retrieval.getHopper41RecipeValue() == 0) System.err.println("pie41 empty");
            else new CommandDispatcher(buncker41Tag).writeSingleRegisterWithoutLock();

            if (recepie.getBunckerShortage41() == 0 && retrieval.getShortageHopper41FactValue() == 0) System.err.println("short41 empty");
            else new CommandDispatcher(shortageBuncker41Tag).writeSingleRegisterWithoutLock();

            if (recepie.getBunckerRecepie42() == 0 && retrieval.getHopper42RecipeValue() == 0) System.err.println("pie42 empty");
            else new CommandDispatcher(buncker42Tag).writeSingleRegisterWithoutLock();

            if (recepie.getBunckerShortage42() == 0 && retrieval.getShortageHopper42FactValue() == 0) System.err.println("short42 empty");
            else new CommandDispatcher(shortageBuncker42Tag).writeSingleRegisterWithoutLock();

            if (recepie.getFibra() == 0 && retrieval.getRecepieFibraValue() == 0) System.err.println("pieFibra empty");
            else new CommandDispatcher(fibraTag).writeSingleRegisterWithoutLock();

            if (recepie.getdDryCh() == 0 && retrieval.getRecepieDDryChValue() == 0) System.err.println("pieDDryCh empty");
            else new CommandDispatcher(dDryChTag).writeSingleRegisterWithoutLock();

            if (recepie.getWater1Recepie() == 0 && retrieval.getWaterRecipeValue() == 0) System.err.println("pieWater1 empty");
            else new CommandDispatcher(waterTag).writeSingleRegisterWithoutLock();

            if (recepie.getWater2Recepie() != 0) new CommandDispatcher(water2Tag).writeSingleRegisterWithoutLock();

            if (recepie.getWater1Shortage() != 0) new CommandDispatcher(shortageWater1).writeSingleRegisterWithoutLock();
            if (recepie.getWater2Shortage() != 0) new CommandDispatcher(shortageWater2).writeSingleRegisterWithoutLock();

            if (recepie.getSilosRecepie1() == 0 && retrieval.getCement1RecipeValue() == 0) System.err.println("pieSilos1 empty");
            else new CommandDispatcher(silos1Tag).writeSingleRegisterWithoutLock();

            if (recepie.getSilosRecepie2() == 0 && retrieval.getCement2RecipeValue() == 0) System.err.println("pieSilos2 empty");
            else new CommandDispatcher(silos2Tag).writeSingleRegisterWithoutLock();

            if (recepie.getSilosShortage1() == 0 && retrieval.getShortageSilos1Value() == 0) System.err.println("shortSilos1 empty");
            else new CommandDispatcher(shortageSilos1).writeSingleRegisterWithoutLock();

            if (recepie.getSilosShortage2() == 0 && retrieval.getShortageSilos2Value() == 0) System.err.println("pieSilos2 empty");
            else new CommandDispatcher(shortageSilos2).writeSingleRegisterWithoutLock();

            if (recepie.getChemyRecepie1() == 0 && retrieval.getChemy1RecipeValue() == 0) System.err.println("chemy1 empty");
            else new CommandDispatcher(chemy1Tag).writeSingleRegisterWithoutLock();

            if (recepie.getChemy2Recepie() == 0 && retrieval.getChemy2RecipeValue() == 0) System.err.println("chemy2 empty");
            else new CommandDispatcher(chemy2Tag).writeSingleRegisterWithoutLock();

            if (recepie.getChemy3Recepie() != 0) new CommandDispatcher(chemy3Tag).writeSingleRegisterWithoutLock();

            if (recepie.getChemyShortage1() == 0 && retrieval.getShortageChemy1Value() == 0) System.err.println("chemy1 shortage empty");
            else new CommandDispatcher(shortageChemy1).writeSingleRegisterWithoutLock();

            if (recepie.getChemyShortage2() == 0 && retrieval.getShortageChemy2Value() == 0) System.err.println("chemy2 shortage empty");
            else new CommandDispatcher(shortageChemy2).writeSingleRegisterWithoutLock();

            if ((recepie.getPathToHumidity() == 0) && (retrieval.getDwplRecepieMix() == 0)) System.out.println("no dwpl");
            else new CommandDispatcher(dwplRecepie).writeSingleRegisterWithoutLock();

            new CommandDispatcher(humidity11Tag).writeSingleRegisterWithoutLock();

            new CommandDispatcher(humidity12Tag).writeSingleRegisterWithoutLock();
            new CommandDispatcher(humidity21Tag).writeSingleRegisterWithoutLock();
            new CommandDispatcher(humidity22Tag).writeSingleRegisterWithoutLock();

            new CommandDispatcher(humidity31Tag).writeSingleRegisterWithoutLock();
            new CommandDispatcher(humidity32Tag).writeSingleRegisterWithoutLock();
            new CommandDispatcher(humidity41Tag).writeSingleRegisterWithoutLock();

            new CommandDispatcher(humidity42Tag).writeSingleRegisterWithoutLock();

            if (recepie.getTimeMix() != 0) {
                timeMixTag.setDIntValueIf(recepie.getTimeMix() * 1000);
                new CommandDispatcher(timeMixTag).writeSingleRegisterWithoutLock();
            }

            preDosageWaterPercent = recepie.getPreDosingWaterPercent();

            if (recepie.getAmperageFluidity() != 0) zoneMixingEnd = recepie.getAmperageFluidity();

            if (preDosageWaterPercent < 100){
                float recalcWaterRecepie = recepie.getWater1Recepie();
                if (preDosageWaterPercent != 0) recalcWaterRecepie = new MathUtils().reCalcValueForRecent(recepie.getWater1Recepie(), preDosageWaterPercent);
                waterTag.setRealValueIf(recalcWaterRecepie);
                new CommandDispatcher(waterTag).writeSingleRegisterWithoutLock();
            }

            lockStateRequests = false;

            Thread.sleep(100);
            new CommandDispatcher(tagListManual.get(175)).writeSingleRegisterWithValue(true);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
