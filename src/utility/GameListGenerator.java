package utility;

import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import objects.Bot;
import objects.Map;

public class GameListGenerator 
{

	public static void GenerateGames(int rounds, Vector<Map> maps, String MapSelection, Vector<Bot> bots, String TournamentType) 
	{
		try 
		{
			FileWriter fstream = new FileWriter("games.txt");
			
			BufferedWriter out = new BufferedWriter(fstream);
			
			boolean randomMap = MapSelection.equalsIgnoreCase("Random");
			if(TournamentType.equalsIgnoreCase("1VsAll"))
			{
				generate1VsAll(rounds, maps, randomMap, bots, out);
			}
			else
			{
				generateRoundRobin(rounds, maps, randomMap, bots, out);
			}
			
			out.write("");
			out.flush();
			out.close();
			
			System.out.println("Generation Complete");
			
		} 
		catch (Exception e) 
		{
			System.err.println("Error: " + e.getMessage());
		}
	}

	public static void generateRoundRobin(int rounds, Vector<Map> maps, boolean randomMap, Vector<Bot> bots, BufferedWriter out) throws IOException 
	{
		int roundNum = 0;
		
		List<Object[]> games = new LinkedList<>();
		for (int i = 0; i < rounds; i++) 
		{
			for (int j = 0; j < bots.size(); j++) 
			{
				for (int k = j+1; k < bots.size(); k++) 
				{
					List<Map> myMaps = new ArrayList<>(maps);
					if(randomMap) {
						Map selected = myMaps.get(ThreadLocalRandom.current().nextInt(myMaps.size()));
						myMaps = new ArrayList<>(1);
						myMaps.add(selected);
					}
					for(Map m : myMaps)
					{
						if (roundNum % 2 == 0) 
						{
							games.add(new Object[] { roundNum, bots.get(j).getName(), bots.get(k).getName(), m.getMapName() });
						}
						else 
						{
							games.add(new Object[] { roundNum, bots.get(k).getName(), bots.get(j).getName(), m.getMapName() });
						}
					}
				}
			}
			roundNum++;
		}
		
		int gameID = 0;
		Collections.shuffle(games);
		for (Object[] game : games)
		{
			out.write(String.format("%7d %5d %20s %20s %35s", gameID, game[0], game[1], game[2], game[3]) + System.getProperty("line.separator"));
			gameID++;
		}
	}
	public static void generate1VsAll(int rounds, Vector<Map> maps, boolean randomMap, Vector<Bot> bots, BufferedWriter out) throws IOException 
	{
		int gameID = 0;
		int roundNum = 0;
		
		for (int i = 0; i < rounds; i++) 
		{
			for (int k = 1; k < bots.size(); k++) 
			{
				List<Map> myMaps = new ArrayList<>(maps);
				if(randomMap) {
					Map selected = myMaps.get(ThreadLocalRandom.current().nextInt(myMaps.size()));
					myMaps = new ArrayList<>(1);
					myMaps.add(selected);
				}
				for(Map m : myMaps)
				{
					out.write(String.format("%7d %5d %20s %20s %35s", gameID, roundNum, bots.get(0).getName(), bots.get(k).getName(), m.getMapName()) + System.getProperty("line.separator"));
					gameID++;
				}
			}
			roundNum++;
		}
	}
}
