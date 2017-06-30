package tr.dev.comtetition;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class NewSortPractice
{

	/** ソートの元となるファイルパス */
	private static final String FILE_INPUT = "../SortPractice/resource/test01.csv";

	/** ソートしたものを書き込むファイルパス */
	private static final String FILE_OUTPUT = "../SortPractice/resource/TestOutput.csv";

	public static void main(String[] args)
	{

		File fileIn = new File(FILE_INPUT);
		File fileOut = new File(FILE_OUTPUT);

		long start = System.currentTimeMillis();
		System.out.println("---START---");
		try
		{
			String headerStr = fileHeader(fileIn,fileOut);
			Map<Integer, String> map = fileMap(fileIn,headerStr);
			Object[] mapKey = fileSetKey(map);
			Map<Integer, String> map2 = fileSort(headerStr,map,mapKey);
			writer(headerStr, map, map2, mapKey);
		}
		catch (IOException e)
		{
			System.out.println("meinメソッド："+e);
		}
		System.out.println("---CSV Output Done---");
		System.out.println("---END---");
		//時間計測終了
		long end = System.currentTimeMillis();
		System.out.println("TIME:" + (end - start)  + "ms");
	}

	/**
	 * 読み込んだファイルからヘッダを取り出すメソッド
	 * @param fileIn
	 * @param fileOut
	 * @return headerStr
	 * @throws IOException
	 */
	private static String fileHeader(File fileIn,File fileOut) throws IOException
	{
		if(fileOut.exists()) fileOut.delete();
		String headerStr = null;
		BufferedReader br = null;
		try
		{
			br = new BufferedReader(new FileReader(fileIn));
			headerStr = br.readLine();
		}catch (IOException e)
		{
			throw new IOException("fileHeader:"+ e);
		}
		finally
		{
			br.close();
		}
		return headerStr;
	}

	/**
	 * * ソートしたい値をMapに格納するメソッド
	 * @param fileIn
	 * @param headerStr
	 * @return map
	 * @throws IOException
	 */
	private static Map<Integer, String> fileMap(File fileIn,String headerStr) throws IOException
	{

		Map<Integer, String> map = new HashMap<>();
		BufferedReader br = null;
		try
		{
			String line;

			br = new BufferedReader(new FileReader(fileIn));
			while ((line = br.readLine()) != null)
			{
				if(line.equals(headerStr)) continue;
				String[] data = line.split(",");
				Integer valueNum = Integer.parseInt(data[0]);
				map.put(valueNum, line);
			}
		}
		catch (IOException e)
		{
			throw new IOException("fileMap:"+ e);
		}
		finally
		{
			br.close();
		}
		return map;
	}
	/**
	 * mapから会員No.の値をObject[]に変換するメソッド
	 * @return mapKey
	 */
	private static Object[] fileSetKey(Map<Integer, String> map)
	{
		Set<Integer> itemIdSet = map.keySet();
		Object[] mapKey = itemIdSet.toArray();

		return mapKey;
	}
	/**
	 * mapKeyに格納された会員No全てを1つずつ取り出してソートするメソッド
	 * @param headerStr
	 * @param map
	 * @param mapKey
	 * @return map2
	 */
	private static Map<Integer, String> fileSort(String headerStr,Map<Integer, String> map,Object[] mapKey){

		Map<Integer, String> map2 = new HashMap<Integer, String>();

		for (int n = 0; n < mapKey.length; n++)
		{
			for (int m = mapKey.length-1; m > n; m--)
			{
				if (Integer.parseInt(mapKey[m].toString()) < Integer.parseInt(mapKey[m-1].toString()))
				{
					Object t = mapKey[m];
					mapKey[m] = mapKey[m-1];
					mapKey[m-1] = t;
				}
			}
			map2.put(Integer.parseInt(mapKey[n].toString()), map.get(mapKey[n]));
		}
		return map2;
	}
	/**
	 * 書き込みをするメソッド
	 * @param headerStr
	 * @param map
	 * @param map2
	 * @param mapKey
	 * @return printWriter
	 * @throws IOException
	 */
	private static PrintWriter writer(String headerStr,Map<Integer, String> map,Map<Integer, String> map2,Object[] mapKey)
			throws IOException
	{
		FileWriter fileWrite = new FileWriter(FILE_OUTPUT, false);
		PrintWriter printWriter = new PrintWriter(new BufferedWriter(fileWrite));

		System.out.println(headerStr);
		printWriter.println(headerStr);
		for (int a = 0; a < map2.size(); a++ )
		{
			printWriter.println(map.get(mapKey[a]));
			System.out.println(map.get(mapKey[a]));
		}
		printWriter.println();
		printWriter.close();
		return printWriter;
	}
}
