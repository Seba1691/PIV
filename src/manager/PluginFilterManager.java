package manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class PluginFilterManager {

	private static PluginFilterManager instance = null;

	private HashMap<String, String> filtrosPreProcesamiento;
	private HashMap<String, String> filtrosPIVProcesamiento;
	private HashMap<String, String> filtrosPostProcesamiento;
	private HashMap<String, String> filtrosVisualizacion;

	private URLClassLoader filtersClassLoader;

	protected PluginFilterManager() throws ManagerException {
		filtrosPreProcesamiento = new HashMap<>();
		filtrosPIVProcesamiento = new HashMap<>();
		filtrosPostProcesamiento = new HashMap<>();
		filtrosVisualizacion = new HashMap<>();
		loadFilters();
	}

	public static PluginFilterManager getInstance() throws ManagerException {
		if (instance == null) {
			instance = new PluginFilterManager();
		}
		return instance;
	}

	public static void reloadInstance() throws ManagerException {
		instance = new PluginFilterManager();
	}

	public HashMap<String, String> getFiltrosPreProcesamiento() {
		return filtrosPreProcesamiento;
	}

	public void setFiltrosPreProcesamiento(HashMap<String, String> filtrosPreProcesamiento) {
		this.filtrosPreProcesamiento = filtrosPreProcesamiento;
	}

	public HashMap<String, String> getFiltrosPIVProcesamiento() {
		return filtrosPIVProcesamiento;
	}

	public void setFiltrosPIVProcesamiento(HashMap<String, String> filtrosPIVProcesamiento) {
		this.filtrosPIVProcesamiento = filtrosPIVProcesamiento;
	}

	public HashMap<String, String> getFiltrosPostProcesamiento() {
		return filtrosPostProcesamiento;
	}

	public void setFiltrosPostProcesamiento(HashMap<String, String> filtrosPostProcesamiento) {
		this.filtrosPostProcesamiento = filtrosPostProcesamiento;
	}

	public HashMap<String, String> getFiltrosVisualizacion() {
		return filtrosVisualizacion;
	}

	public void setFiltrosVisualizacion(HashMap<String, String> filtrosVisualizacion) {
		this.filtrosVisualizacion = filtrosVisualizacion;
	}

	public URLClassLoader getFiltersClassLoader() {
		return filtersClassLoader;
	}

	public void setFiltersClassLoader(URLClassLoader filtersClassLoader) {
		this.filtersClassLoader = filtersClassLoader;
	}

	public void putPreProcesingFilter(String filterClass, String filterName) {
		filtrosPreProcesamiento.put(filterClass, filterName);
	}

	public void putPIVProcesingFilter(String filterClass, String filterName) {
		filtrosPIVProcesamiento.put(filterClass, filterName);
	}

	public void putPostProcesingFilter(String filterClass, String filterName) {
		filtrosPostProcesamiento.put(filterClass, filterName);
	}

	public void putVisualizationFilter(String filterClass, String filterName) {
		filtrosVisualizacion.put(filterClass, filterName);
	}

	public void putFilter(String filterClass, String type, String filterName) {
		switch (type) {
		case Constants.filtroPreProcesamiento:
			filtrosPreProcesamiento.put(filterClass, filterName);
			break;
		case Constants.filtroPIVProcesamiento:
			filtrosPIVProcesamiento.put(filterClass, filterName);
			break;
		case Constants.filtroPostProcesamiento:
			filtrosPostProcesamiento.put(filterClass, filterName);
			break;
		case Constants.filtroVisualizacion:
			filtrosVisualizacion.put(filterClass, filterName);
			break;
		}
	}

	private void loadFilters() throws ManagerException {
		try {
			File dir = new File(Settings.filtersPath);
			List<URL> filtersURL = new ArrayList<>();
			for (File file : dir.listFiles()) {

				InputStream config = getInputStream(file, Constants.fileConfig);

				if (config == null)
					continue;

				filtersURL.add(new URL("file:///" + Settings.filtersPath + "/" + file.getName()));
				BufferedReader br = new BufferedReader(new InputStreamReader(config, "UTF-8"));
				String linea;
				while ((linea = br.readLine()) != null) {
					String[] filterData = linea.split(":");
					putFilter(filterData[0], filterData[1], filterData[2]);
				}
			}
			setFiltersClassLoader(new URLClassLoader(filtersURL.toArray(new URL[filtersURL.size()])));
		} catch (Exception e) {
			throw new ManagerException("Error al cargar los filtros desde " + Settings.filtersPath, e);
		}
	}

	private InputStream getInputStream(File zip, String entry) throws IOException {
		ZipInputStream zin = new ZipInputStream(new FileInputStream(zip));
		for (ZipEntry e; (e = zin.getNextEntry()) != null;) {
			if (e.getName().equals(entry)) {
				return zin;
			}
		}
		return null;
	}

}