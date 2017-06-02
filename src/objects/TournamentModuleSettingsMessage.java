package objects;
import java.util.Vector;

public class TournamentModuleSettingsMessage implements Message 
{
	private static final long serialVersionUID = 9062722131163042773L;
	
	public int 					LocalSpeed				= 0;
	public int 					FrameSkip				= 0;
	public int					GameFrameLimit			= 86400;
	public int					ZeroSpeedTime 			= 43200;
	public int					NoKillsRealSecondsLimit = 300;
	public int					InitMaxSpeedTime 		= 1440;
	public int					NoCombatSpeedUpTime     = 9600;
	public int					NoCombatSpeedUpDelay    = 480;
	public int					CameraMoveTime     	    = 250;
	public int					CameraMoveTimeMin       = 50;
	public int					WatchScoutWorkerUntil   = 7500;
	
	public Vector<Integer>		TimeoutLimits			= new Vector<Integer>();
	public Vector<Integer>		TimeoutBounds			= new Vector<Integer>();
	
	public String				DrawBotNames			= "true";
	public String				DrawTournamentInfo		= "true";
	public String				DrawUnitInfo			= "true";
	
	public TournamentModuleSettingsMessage()
	{
		
	}
	
	public String toString()
	{
		return "(" + LocalSpeed + "," + FrameSkip + "," + GameFrameLimit + ")";
	}
	
	public String getSettingsFileString()
	{
		String newLine = System.getProperty("line.separator");
		
		String tm = "";
		
		tm += "LocalSpeed "         + LocalSpeed     + newLine;
		tm += "FrameSkip "          + FrameSkip      + newLine;
		tm += "GameFrameLimit "     + GameFrameLimit + newLine;
		tm += "ZeroSpeedTime "      + ZeroSpeedTime + newLine;
		tm += "NoKillsRealSecondsLimit " + NoKillsRealSecondsLimit + newLine;
		tm += "InitMaxSpeedTime "   + InitMaxSpeedTime + newLine;
		tm += "NoCombatSpeedUpTime " + NoCombatSpeedUpTime + newLine;
		tm += "NoCombatSpeedUpDelay " + NoCombatSpeedUpDelay + newLine;
		tm += "CameraMoveTime "     + CameraMoveTime   + newLine;
		tm += "CameraMoveTimeMin "  + CameraMoveTimeMin + newLine;
		tm += "WatchScoutWorkerUntil " + WatchScoutWorkerUntil + newLine;
		tm += "DrawBotNames "       + DrawBotNames     + newLine;
		tm += "DrawTournamentInfo " + DrawTournamentInfo + newLine;
		tm += "DrawUnitInfo "       + DrawUnitInfo     + newLine;
		
		for (int i = 0; i < TimeoutLimits.size(); ++i)
		{
			tm += "Timeout " + TimeoutLimits.get(i) + " " + TimeoutBounds.get(i) + newLine;	
		}
		
		return tm;
	}
}
