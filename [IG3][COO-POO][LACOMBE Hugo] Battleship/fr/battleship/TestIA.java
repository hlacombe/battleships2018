package fr.battleship;

import lacombe.hugo.*;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

public class TestIA {

	public static void main(String[] args) {
		

		
		int nbWin1v2 = 0;
		int nbWin1v3 = 0;
		int nbWin2v3 = 0;
		
		int moyenne=0;
		
		int mode[] = new int[3];
		for(int i=0;i<300; i++) {
			mode[0]=3;
			if(i<100){ mode[1]=1;mode[2]=2;}
			if(i<200){ mode[1]=1;mode[2]=2;}
			if(i<300){ mode[1]=1;mode[2]=2;}
			
			Game game = new Game(mode);
			game.getP1().placeFleet();
			game.getP2().placeFleet();
			while(!(game.endGame())) {
				game.guess();
				game.upTurnCount();
			}
			
			if(i<100){ if(game.winner()==game.getP1() ) {nbWin1v2++;}}
			if(i<200){ if(game.winner()==game.getP1() ) {nbWin1v3++;}}
			if(i<300){ if(game.winner()==game.getP1() ) {nbWin2v3++;}}
			
			moyenne=moyenne+(game.getTurnCount()/2);
		}
		
		FileWriter fw = null;
		String pwd = Paths.get("ai_proofs.csv").toAbsolutePath().normalize().toString();

		try {
			System.out.println(pwd);
			
			fw = new FileWriter(pwd);
			
			String toWrite ="";
			toWrite = toWrite+"AI NAME;score;AI NAME;score2\n";
			toWrite = toWrite+"AI Beginner;"+nbWin1v2+";AI Medium;"+(100-nbWin1v2)+"\n";
			toWrite = toWrite+"AI Beginner;"+nbWin1v3+";AI Hard;"+(100-nbWin1v3)+"\n";
			toWrite = toWrite+"AI Medium;"+nbWin2v3+";AI Hard;"+(100-nbWin2v3)+"\n";
			
			fw.write(toWrite);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Error : cannot find current directory path\nTry to check access rights");
			e.printStackTrace();
		}
		finally{
			if(fw !=null) {
				try {
					fw.close();
					System.out.println("File :"+pwd+" created successfully");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("Error : cannot close document correctly.");
				}
			}
		}

	}
}
