package akyDroid.gameFramework.implementation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import akyDroid.gameFramework.FileIO;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Environment;
import android.preference.PreferenceManager;

public class GameFileIO implements FileIO{

    Context myContext;
    AssetManager myAssets;
    String myExternalStoragePath;
	
    public GameFileIO(Context context){
    	myContext = context;
    	myAssets = context.getAssets();
    	myExternalStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
    }
    
	@Override
	public InputStream readFile(String file) throws IOException {
		return new FileInputStream(myExternalStoragePath + file);
	}

	@Override
	public OutputStream writeFile(String file) throws IOException {
		return new FileOutputStream(myExternalStoragePath + file);
	}

	@Override
	public InputStream readAsset(String file) throws IOException {
		return myAssets.open(file);
	}

	@Override
	public SharedPreferences getSharedPref() {
		return PreferenceManager.getDefaultSharedPreferences(myContext);
	}
	

}
