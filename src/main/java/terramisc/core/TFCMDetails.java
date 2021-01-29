package terramisc.core;

public class TFCMDetails 
{
	public static final String ModID = "tfcm";
	public static final String ModName = "TerraMisc";

	public static final int VersionMajor = 0;
	public static final int VersionMinor = 14;
	public static final int VersionRevision = 4;

	public static final String ModVersion = VersionMajor + "." + VersionMinor + "." + VersionRevision;

	public static final String ModDependencies = "required-after:terrafirmacraftplus" + "required-after:Waila";
	public static final String ModChannel = "TerraMisc";
	public static final String SERVER_PROXY_CLASS = "terramisc.core.TFCMCommonProxy";
	public static final String CLIENT_PROXY_CLASS = "terramisc.core.TFCMClientProxy";
	
	public static final String AssetPath = "/assets/" + ModID + "/";
	public static final String AssetPathGui = "textures/gui/";
	
	public static final String ConfigFilePath = "/config/";
	public static final String ConfigFileName = "TerraMisc.cfg";
	
	public static final int GuiOffset = 10000;
	
	public static final String MODID_NEI = "NotEnoughItems";
	public static final String MODID_TFC = "terrafirmacraftplus";
	public static final String MODID_WAILA = "Waila";

	public static final String MODNAME_NEI = "Not Enough Items";
	public static final String MODNAME_TFC = "TerraFirmaCraft";
	public static final String MODNAME_WAILA = "Waila";
}
