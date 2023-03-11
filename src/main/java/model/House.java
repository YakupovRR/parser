package model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class House {
    @NonNull
    Integer id;
    @NonNull
    String name;
    String description;
    Integer square;
    Integer rooms;
    Integer width;
    Integer length; // вглубь участка
    Floors floors;
    boolean groundFloor;
    Integer oldId;
    List<String> tags;
    List<String> planImagesUrls;
    List<String> houseImagesUrls;
    List<String> features;
}
