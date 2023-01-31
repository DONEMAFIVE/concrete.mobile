package ru.zzbo.concretemobile.protocol.profinet.commands;

import static ru.zzbo.concretemobile.utils.Constants.tagListManual;
import static ru.zzbo.concretemobile.utils.Constants.lockStateRequests;

import ru.zzbo.concretemobile.models.Recepie;
import ru.zzbo.concretemobile.protocol.profinet.models.Tag;

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

            //блокировка
            lockStateRequests = true;

            new CommandDispatcher(buncker11Tag).writeSingleRegisterWithoutLock();
            new CommandDispatcher(shortageBuncker11Tag).writeSingleRegisterWithoutLock();
            new CommandDispatcher(buncker12Tag).writeSingleRegisterWithoutLock();
            new CommandDispatcher(shortageBuncker12Tag).writeSingleRegisterWithoutLock();
            new CommandDispatcher(buncker21Tag).writeSingleRegisterWithoutLock();
            new CommandDispatcher(shortageBuncker21Tag).writeSingleRegisterWithoutLock();
            new CommandDispatcher(buncker22Tag).writeSingleRegisterWithoutLock();
            new CommandDispatcher(shortageBuncker22Tag).writeSingleRegisterWithoutLock();
            new CommandDispatcher(buncker31Tag).writeSingleRegisterWithoutLock();
            new CommandDispatcher(shortageBuncker31Tag).writeSingleRegisterWithoutLock();
            new CommandDispatcher(buncker32Tag).writeSingleRegisterWithoutLock();
            new CommandDispatcher(shortageBuncker32Tag).writeSingleRegisterWithoutLock();
            new CommandDispatcher(buncker41Tag).writeSingleRegisterWithoutLock();
            new CommandDispatcher(shortageBuncker41Tag).writeSingleRegisterWithoutLock();
            new CommandDispatcher(buncker42Tag).writeSingleRegisterWithoutLock();
            new CommandDispatcher(shortageBuncker42Tag).writeSingleRegisterWithoutLock();
            new CommandDispatcher(waterTag).writeSingleRegisterWithoutLock();
            new CommandDispatcher(water2Tag).writeSingleRegisterWithoutLock();
            new CommandDispatcher(shortageWater1).writeSingleRegisterWithoutLock();
            new CommandDispatcher(shortageWater2).writeSingleRegisterWithoutLock();
            new CommandDispatcher(silos1Tag).writeSingleRegisterWithoutLock();
            new CommandDispatcher(silos2Tag).writeSingleRegisterWithoutLock();
            new CommandDispatcher(shortageSilos1).writeSingleRegisterWithoutLock();
            new CommandDispatcher(shortageSilos2).writeSingleRegisterWithoutLock();
            new CommandDispatcher(chemy1Tag).writeSingleRegisterWithoutLock();
            new CommandDispatcher(chemy2Tag).writeSingleRegisterWithoutLock();
            new CommandDispatcher(chemy3Tag).writeSingleRegisterWithoutLock();
            new CommandDispatcher(shortageChemy1).writeSingleRegisterWithoutLock();
            new CommandDispatcher(shortageChemy2).writeSingleRegisterWithoutLock();
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
            lockStateRequests = false;

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
