import lombok.extern.slf4j.Slf4j;
import model.House;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Slf4j
public class GetHouseProject {

    // TODO: 04.03.2023
    // Сделать через stitch case
    //

    private String str;

    private final List<String> requiredParameters = new ArrayList<>();
    private final Map<String, String> findParameters = new HashMap<>();

    private final House house = new House();

    public void projectMapper() {
        if (requiredParameters.isEmpty()) setRequiredParameters();
        clearFindedParameters();
        try {
            BufferedReader in = new BufferedReader(new FileReader("project.html"));
            while ((str = in.readLine()) != null) {
                if (str.contains("<title>")) {
                    house.setName(findName(in));
                }
                if (str.contains("Общая площадь")) {
                    Integer s = findSquare(in);
                    house.setSquare(s);
                }
                if (str.contains("Жилых комнат")) {
                    Integer rooms = findRooms(in);
                    house.setRooms(rooms);
                }
                if (str.contains("Габариты")) {
                    List<Integer> sizes = findSizes(in);
                    house.setWidth(sizes.get(0));
                    house.setLength(sizes.get(1));
                }
                if (str.contains("Номер проекта")) {
                    Integer oldId = findOldId(in);
                    house.setOldId(oldId);
                }
                if (str.contains("Категория:")) {
                    house.setTags(findTags(in));
                }
                if (str.contains("Планировка")) {
                    house.setPlanImagesUrls(findPlanImagesUrls(in));
                }
                if (str.contains("<div class=\"swiper-container gallery-slider\">")) { //картинки дома
                    house.setHouseImagesUrls(findHouseImagesUrls(in));
                }

                if (str.contains("Особенности проекта")) {
                    house.setFeatures(findFiatures(in));
                }

            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(house);
    }

    private String findName(BufferedReader in) throws IOException {
        str = in.readLine();
        String[] l = str.split("«");
        String[] ll = l[1].split("»");
        return ll[0];
    }

    private Integer findRooms(BufferedReader in) throws IOException {
        str = in.readLine();
        str = in.readLine();
        String[] l = str.split(" ");
        return Integer.parseInt(l[0]);
    }

    private Integer findSquare(BufferedReader in) throws IOException {
        str = in.readLine();
        str = in.readLine();
        String[] l = str.split(" "); //читаем до пробела
        return Integer.parseInt(l[0]);
    }

    private List<Integer> findSizes(BufferedReader in) throws IOException {
        List<Integer> sizes = new ArrayList<>();
        str = in.readLine();
        str = in.readLine();
        String[] l = str.split(" "); //читаем до пробела
        Integer a = Integer.parseInt(l[0]);
        String stringB = l[1].substring(1);
        Integer b = Integer.parseInt(stringB);
        sizes.add(a);
        sizes.add(b);
        return sizes;
    }

    private Integer findOldId(BufferedReader in) throws IOException {
        str = in.readLine();
        str = in.readLine();
        String[] l = str.split(" ");
        return Integer.parseInt(l[0]);
    }

    private List<String> findTags(BufferedReader in) throws IOException {
        List<String> tags = new ArrayList<>();
        str = in.readLine();
        String[] stringWithMainTag = str.split("</a>");
        String mainTag = stringWithMainTag[0].trim();         //он в дивах, поэтому отдельно
        tags.add(mainTag);
        str = in.readLine();
        str = in.readLine();
        str = in.readLine();
        str = in.readLine();
        while (!str.contains("</div>")) {
            str = in.readLine();
            String firsChar = String.valueOf(str.charAt(0));
            if (!firsChar.equals("<") && !(firsChar.equals(" "))) {
                String[] stringWithTag = str.split("</span>");
                String tag = stringWithTag[0].trim();
                tags.add(tag);
            }
        }
        return tags;
    }

    private List<String> findPlanImagesUrls(BufferedReader in) throws IOException {
        List<String> findedPlanImagesUrls = new ArrayList<>();
        str = in.readLine();
        while (!str.contains("Построенные объекты")) {
            str = in.readLine();
            if (str.contains("href")) {
                String[] stringWithTag = str.split("\"");
                String url = stringWithTag[1].trim();
                findedPlanImagesUrls.add(url);
            }
        }


        return findedPlanImagesUrls;

    }

    private List<String> findHouseImagesUrls(BufferedReader in) throws IOException {
        List<String> houseImagesUrls = new ArrayList<>();
        str = in.readLine();
        while (!str.contains("project-item-like hidden-print")) {
            str = in.readLine();
            if (str.contains("href")) {
                String[] stringWithTag = str.split("\"");
                String url = stringWithTag[1].trim();
                houseImagesUrls.add(url);
            }
        }
        return houseImagesUrls;
    }

    private List<String> findFiatures(BufferedReader in) throws IOException {
        List<String> fiatures = new ArrayList<>();
        str = in.readLine();
        str = in.readLine();
        str = in.readLine();
        while (!str.contains("</ul>")) {
            str = in.readLine();
            String firsChar = String.valueOf(str.charAt(0));
            if (!firsChar.equals("<") && !(firsChar.equals(" "))) {
                String[] stringWithFiature = str.split("</li>");
                String fiature = stringWithFiature[0].trim();
                fiatures.add(fiature);
            }
        }
        System.out.println("Найдено фич: " + fiatures.size());
        System.out.println("Первая: " + fiatures.get(0));
        System.out.println("Последняя: " + fiatures.get((fiatures.size() - 1)));
        return fiatures;
    }

    private void setRequiredParameters() {


        requiredParameters.add("Общая площадь");
    }

    private void clearFindedParameters() {
        findParameters.clear();
        for (String i : requiredParameters) {
            findParameters.put(i, null);
        }
    }
}
