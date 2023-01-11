package ru.zzbo.concretemobile.protocol.profinet.commands;

import static ru.zzbo.concretemobile.utils.Constants.tagListManual;
import static ru.zzbo.concretemobile.utils.Constants.lockStateRequests;

import ru.zzbo.concretemobile.models.Recipe;
import ru.zzbo.concretemobile.protocol.profinet.models.Tag;

public class SetRecipe {

    public boolean sendRecipeToPLC(Recipe recipe) {
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

            buncker11Tag.setRealValueIf(recipe.getBunckerRecepie11());
            shortageBuncker11Tag.setRealValueIf(recipe.getBunckerShortage11());
            buncker12Tag.setRealValueIf(recipe.getBunckerRecepie12());
            shortageBuncker12Tag.setRealValueIf(recipe.getBunckerShortage12());

            buncker21Tag.setRealValueIf(recipe.getBunckerRecepie21());
            shortageBuncker21Tag.setRealValueIf(recipe.getBunckerShortage21());
            buncker22Tag.setRealValueIf(recipe.getBunckerRecepie22());
            shortageBuncker22Tag.setRealValueIf(recipe.getBunckerShortage22());

            buncker31Tag.setRealValueIf(recipe.getBunckerRecepie31());
            shortageBuncker31Tag.setRealValueIf(recipe.getBunckerShortage31());
            buncker32Tag.setRealValueIf(recipe.getBunckerRecepie32());
            shortageBuncker32Tag.setRealValueIf(recipe.getBunckerShortage32());

            buncker41Tag.setRealValueIf(recipe.getBunckerRecepie41());
            shortageBuncker41Tag.setRealValueIf(recipe.getBunckerShortage41());
            buncker42Tag.setRealValueIf(recipe.getBunckerRecepie42());
            shortageBuncker42Tag.setRealValueIf(recipe.getBunckerShortage42());

            waterTag.setRealValueIf(recipe.getWater1Recepie());
            water2Tag.setRealValueIf(recipe.getWater2Recepie());
            chemy1Tag.setRealValueIf(recipe.getChemyRecepie1());
            chemy2Tag.setRealValueIf(recipe.getChemy2Recepie());

            silos1Tag.setRealValueIf(recipe.getSilosRecepie1());
            silos2Tag.setRealValueIf(recipe.getSilosRecepie2());

            shortageWater1.setRealValueIf(recipe.getWater1Shortage());
            shortageWater2.setRealValueIf(recipe.getWater2Shortage());
            shortageChemy1.setRealValueIf(recipe.getChemyShortage1());
            shortageChemy2.setRealValueIf(recipe.getChemyShortage2());
            shortageSilos1.setRealValueIf(recipe.getSilosShortage1());
            shortageSilos2.setRealValueIf(recipe.getSilosShortage2());

            humidity11Tag.setRealValueIf(recipe.getHumidity11());
            humidity12Tag.setRealValueIf(recipe.getHumidity12());
            humidity21Tag.setRealValueIf(recipe.getHumidity21());
            humidity22Tag.setRealValueIf(recipe.getHumidity22());
            humidity31Tag.setRealValueIf(recipe.getHumidity31());
            humidity32Tag.setRealValueIf(recipe.getHumidity32());
            humidity41Tag.setRealValueIf(recipe.getHumidity41());
            humidity42Tag.setRealValueIf(recipe.getHumidity42());

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
            if (recipe.getTimeMix() != 0) {
                timeMixTag.setDIntValueIf(recipe.getTimeMix() * 1000);
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
