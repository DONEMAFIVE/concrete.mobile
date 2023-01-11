package ru.zzbo.concretemobile.protocol.profinet.collectors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import ru.zzbo.concretemobile.protocol.profinet.models.*;

public class DynamicTagBuilder {
    private List<Tag> tagList;

    List<BlockBool> tagBoolAnswer;
    List<BlockMultiple> tagRealAnswer;
    List<BlockMultiple> tagDIntAnswer;
    List<BlockMultiple> tagIntAnswer;

    public DynamicTagBuilder(List<Tag> tagList) {
        this.tagList = tagList;
    }

    public void buildSortedTags(){

        List<Tag> boolTags = new ArrayList<>();
        List<Tag> realTags = new ArrayList<>();
        List<Tag> intTags = new ArrayList<>();
        List<Tag> dIntTags = new ArrayList<>();

        //раскладывают по типам
        for (Tag tag: tagList) {
            if (tag.getTypeTag().equals("Bool")) boolTags.add(tag);
            if (tag.getTypeTag().equals("Real")) realTags.add(tag);
            if (tag.getTypeTag().equals("Int")) intTags.add(tag);
            if (tag.getTypeTag().equals("DInt")) dIntTags.add(tag);
        }
        System.out.println(dIntTags.size());

        //сортирую
        //bool
        //поиск в списке тэгов какие DBшки используются
        List<Integer> dbBoolAreas = new ArrayList<>();
        for (Tag tag: boolTags) {
            int dbArea = tag.getArea();
            if (!dbBoolAreas.contains(dbArea)) dbBoolAreas.add(dbArea);
        }

        List<Integer> boolNumbers = new ArrayList<>();
        for (Tag tag: boolTags) {
            int number = tag.getDbNumber();
            if (!boolNumbers.contains(number)) boolNumbers.add(number);
        }

        List<Integer> boolStarts = new ArrayList<>();
        for (Tag tag: boolTags) {
            int start = tag.getStart();
            if (!boolStarts.contains(start)) boolStarts.add(start);
        }

        //на выходе в dbBoolAreas имею все доступные в БД зоны для считывания
        //сортировка по типам и по адресам
        List<TagBoolShorts> boolSortedTags = new ArrayList<>();

        for (int dbArea: dbBoolAreas) {

            for (int number : boolNumbers) {

                if (number == 0) continue;  //не может быть 0, такие тэги сразу нужно отбрасывать

                for (int start: boolStarts) {

                    List<Integer> bits = new ArrayList<>();
                    for (int i = 0; i < boolTags.size(); i++) {
                        if ((boolTags.get(i).getArea() == dbArea) && (boolTags.get(i).getStart() == start) && (boolTags.get(i).getDbNumber() == number)) {
                            if (!bits.contains(boolTags.get(i).getBit())) bits.add(boolTags.get(i).getBit());
                        }
                    }
                    if (bits.size() > 0) {
                        Collections.sort(bits);
                        boolSortedTags.add(new TagBoolShorts(
                                dbArea, number, start, bits
                        ));
                    }
                }
            }
        }
        for (TagBoolShorts tag : boolSortedTags) {
            System.out.println("Area: " + tag.getDbArea() +
                    ", Number: " + tag.getNumber() +
                    ", Start: " + tag.getStart() +
                    ", Bits: " + tag.getBits()
            );
        }

        List<TagRealShorts> realSortedList = sortedRealValues(realTags);
        printTagRealShortsList(realSortedList);     //simple check debug
        List<TagRealShorts> intSortedList = sortedRealValues(intTags);
        printTagRealShortsList(intSortedList);      //simple check debug
        List<TagRealShorts> DIntSortedList = sortedRealValues(dIntTags);
        printTagRealShortsList(DIntSortedList);     //simple check debug

        //по факту имею 4 сортированные коллекции для 4-х типов данных
        //BOOL
        List<BlockBool> packetsBool = new ArrayList<>();
        for (TagBoolShorts tag : boolSortedTags) {  //у bool самый короткий шаг и есть биты, упорядочивание остальных типов быдет отличаться

            //вычисление последовательности коллекций <------
            List<Integer> bits = tag.getBits();
            int counter = 0;
            int firstValue = 0;

            if (bits.size() == 1){  //обработка ситуации, когда размер коллекции всего 1
                packetsBool.add(new BlockBool(
                        tag.getDbArea(),
                        tag.getNumber(),
                        tag.getStart(),
                        1,
                        tag.getBits().get(0),
                        1
                ));
                continue;
            }
            for (int i = 0; i < bits.size() - 1; i++){

                if (i == 0) firstValue = bits.get(i);

                int sum = (bits.get(i + 1)) - (bits.get(i));

                if (sum == 1){
                    counter++;
                } else {
                    counter++;
                    packetsBool.add(new BlockBool(
                            tag.getDbArea(),
                            tag.getNumber(),
                            tag.getStart(),
                            1,
                            firstValue,
                            counter
                    ));

                    counter = 0;
                    firstValue = bits.get(i + 1);
                }

                if (i == bits.size() - 2){
                    //дошли до конца, обработка последнего элемента в массиве, но цикл дойдет до сюда при условии, что размер массива будет > 1
                    if (sum == 1) counter++;
                    if (counter == 0) counter++;
                    packetsBool.add(new BlockBool(
                                    tag.getDbArea(),
                                    tag.getNumber(),
                                    tag.getStart(),
                                    1,
                                    firstValue,
                                    counter++
                            )
                    );
                }
            }
        }

        //вывод уже сортированных блоков
        List<BlockMultiple> packetReal = buildRealBlockPacket(realSortedList, 4);

        List<BlockMultiple> packetInt = buildRealBlockPacket(intSortedList, 2);

        List<BlockMultiple> packetDInt = buildRealBlockPacket(DIntSortedList, 4);

        tagBoolAnswer = packetsBool;
        tagRealAnswer = packetReal;
        tagDIntAnswer = packetDInt;
        tagIntAnswer = packetInt;

    }

    private List<TagRealShorts> sortedRealValues(List<Tag> tags){

        List<Integer> areas = new ArrayList<>();
        for (Tag tag: tags) {

            int dbArea = tag.getArea();
            if (!areas.contains(dbArea)) areas.add(dbArea);
        }

        List<Integer> numbers = new ArrayList<>();
        for (Tag tag: tags) {
            int number = tag.getDbNumber();
            if (!numbers.contains(number)) numbers.add(number);
        }

        List<Integer> starts = new ArrayList<>();
        for (Tag tag: tags) {
            int start = tag.getStart();
            if (!starts.contains(start)) starts.add(start);
        }

        List<TagRealShorts> realShortsList = new ArrayList<>();

        for (int dbArea: areas) {

            for (int number : numbers) {

                List<Integer> startsSorted = new ArrayList<>();

                for (int i = 0; i < tags.size(); i++) {
                    if ((tags.get(i).getArea() == dbArea) && (tags.get(i).getDbNumber() == number)) {
                        if (!startsSorted.contains(tags.get(i).getStart())) startsSorted.add(tags.get(i).getStart());
                    }
                }

                Collections.sort(startsSorted);

                realShortsList.add(new TagRealShorts(
                        dbArea, number, startsSorted
                ));
            }
        }
        return realShortsList;
    }

    /**
     * @param tags для real, int, dint
     * @param typeLength передается шаг в массиве блока данных, для конкретного типа,
     * для real - 4, для int - 2, dint - 4,
     * bool расчитывается не здесь, там сдвигается бит и каждые 8 шагов обнуляется с увеличением на +1 целой части
     *
     * @return
     */
    private List<BlockMultiple> buildRealBlockPacket(List<TagRealShorts> tags, int typeLength){

        List<BlockMultiple> packetReal = new ArrayList<>();

        for (TagRealShorts tag : tags){

            List<Integer> starts = tag.getStarts();
            int counter = 0;
            int firstValue = 0;

            if (starts.size() == 1){
                packetReal.add(new BlockMultiple(
                        tag.getDbArea(),
                        tag.getNumber(),
                        starts.get(0),
                        typeLength,
                        1,
                        null
                ));
            }

            for (int i = 0; i < starts.size() - 1; i++) {

                if (i == 0) firstValue = starts.get(i);

                int sum = (starts.get(i + 1)) - (starts.get(i));

                if (sum == typeLength){
                    counter++;
                } else {
                    counter++;
                    packetReal.add(new BlockMultiple(
                                    tag.getDbArea(),
                                    tag.getNumber(),
                                    firstValue,
                                    typeLength,
                                    counter,
                                    null
                            )
                    );
                    counter = 0;
                    firstValue = starts.get(i + 1);
                }

                if (i == starts.size() - 2){
                    if (sum == typeLength) counter++;
                    if ((counter == 0) && (tag.getNumber() != 0) && (tag.getStarts().size() != 0)) counter++;
                    packetReal.add(new BlockMultiple(
                            tag.getDbArea(),
                            tag.getNumber(),
                            firstValue,
                            typeLength,
                            counter++,
                            null
                    ));
                }
            }
        }
        return packetReal;
    }

    private void printBlockBool(List<BlockBool> boolShorts){
        for (BlockBool blockBool : boolShorts) {
            System.out.println("Area: " + blockBool.getDbArea() +
                    " [Number]: " + blockBool.getNumber() +
                    " [Start]:  " + blockBool.getStart() +
                    " [Length]: " + blockBool.getLength() +
                    " [Step]: " + blockBool.getStep() +
                    " [StartBit]: " + blockBool.getStartBit()
            );
        }
    }

    private void printTagRealShortsList(List<TagRealShorts> realShorts){
        for (TagRealShorts tag : realShorts) {
            System.out.println("Area: " + tag.getDbArea() +
                    ", Number: " + tag.getNumber() +
                    ", Start: " + tag.getStarts()
            );
        }
    }

    private void printBlockReal(List<BlockMultiple> realShorts){
        for (BlockMultiple blockMultiple : realShorts) {
            System.out.println("Area: " + blockMultiple.getDbArea() +
                    " [Number]: " + blockMultiple.getNumber() +
                    " [Length]: " + blockMultiple.getLength() +
                    " [Step]: " + blockMultiple.getStep() +
                    " [First Element]:  " + blockMultiple.getStartValue()
            );
        }
    }

    public List<BlockBool> getTagBoolAnswer() {
        return tagBoolAnswer;
    }

    public List<BlockMultiple> getTagRealAnswer() {
        return tagRealAnswer;
    }

    public List<BlockMultiple> getTagDIntAnswer() {
        return tagDIntAnswer;
    }

    public List<BlockMultiple> getTagIntAnswer() {
        return tagIntAnswer;
    }
}
