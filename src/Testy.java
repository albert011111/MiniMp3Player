import java.io.File;

public class Testy {

	public static void main(String[] args) {
		
		File file = new File("C:/Users/User/AppData/Local/SceneBuilder/SceneBuilder.exe");
		String fExt = file.getName().substring(file.getName().lastIndexOf("."));
		System.out.println(fExt);

	}

}
