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
			
			
			if(TournamentType.equalsIgnoreCase("1VsAll"))
			{
				generate1VsAll(rounds, maps, bots, out);
			}
			else
			{
				boolean randomMap = MapSelection.equalsIgnoreCase("Random");
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

		Collections.shuffle(games);
		
		HashMap<String,Integer> counts = new HashMap<>(bots.size());
		double total = bots.size();
		int gameID = 0;
		while(!games.isEmpty()) {
			Iterator<Object[]> iterator = games.iterator();
			while(iterator.hasNext()) {
				Object[] game = iterator.next();
				String bot1 = (String)game[1];
				if(!counts.containsKey(bot1)) {
					counts.put(bot1, 0);
				}
				String bot2 = (String)game[2];
				if(!counts.containsKey(bot2)) {
					counts.put(bot2, 0);
				}
				int expected = (int)Math.ceil((gameID+1)/total);
				if(counts.get(bot1) < expected && counts.get(bot2) < expected) {
					out.write(String.format("%7d %5d %20s %20s %35s", gameID++, game[0], game[1], game[2], game[3]) + System.getProperty("line.separator"));
					iterator.remove();
				}
			}
		}
	}
	public static void generate1VsAll(int rounds, Vector<Map> maps, Vector<Bot> bots, BufferedWriter out) throws IOException 
	{
		int gameID = 0;
		int roundNum = 0;
		
		for (int i = 0; i < rounds; i++) 
		{
			for(Map m : maps)
			{
				for (int k = 1; k < bots.size(); k++) 
				{
					out.write(String.format("%7d %5d %20s %20s %35s", gameID, roundNum, bots.get(0).getName(), bots.get(k).getName(), m.getMapName()) + System.getProperty("line.separator"));
					gameID++;
				}
				roundNum++;
			}
		}
	}
}
