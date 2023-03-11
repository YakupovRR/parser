import java.io.IOException;

public class Main {

    private final static String fileName = "project";

    private final static SavePage savaPage = new SavePage();
    private final static GetHouseProject getHouseProject = new GetHouseProject();

    public static void main(String[] args) throws IOException {

        //    savaPage.savePage();
        getHouseProject.projectMapper();


    }


}
