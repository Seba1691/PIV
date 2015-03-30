package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.imageio.ImageIO;

import jpiv2.FileHandling;
import pivLayer.ElementoProcesable;
import pivLayer.FiltroCorrelacionCruzada;
import pivLayer.FiltroPostProcesamiento;
import pivLayer.FiltroPreProcesamiento;
import pivLayer.FiltroRemplazoVectoresInvalidosPorMediana;
import pivLayer.FiltroSubstract;
import pivLayer.FiltroTestMedianaNormalizada;
import pivLayer.Imagen;
import pivLayer.MapaVectores;
import pivLayer.PostProcesador;
import pivLayer.PreProcesador;
import pivLayer.Procesador;
import pivLayer.ProcesadorPIV;
import pivLayer.Seleccionador;
import pivLayer.SeleccionadorCascada;
import pivLayer.SeleccionadorPares;
import wapper.WrapperException;

public class MainPruebaAdapter {

	public static void main(String[] args) throws IOException, WrapperException {
		BufferedImage image1 = ImageIO.read(new File("C:/Users/Seba/Desktop/PIV/imagen1.png"));
		BufferedImage image2 = ImageIO.read(new File("C:/Users/Seba/Desktop/PIV/imagen2.png"));

		Imagen im1 = new Imagen(image1);
		Imagen im2 = new Imagen(image2);
		//Imagen im3 = new Imagen(image1);
		Imagen im4 = new Imagen(image2);
		List<ElementoProcesable> inputImage = new ArrayList<ElementoProcesable>();
		inputImage.add(im1);
		inputImage.add(im2);
		inputImage.add(im4);

		Seleccionador seleccionador = new SeleccionadorPares(Seleccionador.SELECCIONADOR_SIMPLE);
		
		Seleccionador seleccionadorPre = new SeleccionadorCascada(Seleccionador.SELECCIONADOR_DOBLE);

		List<FiltroPreProcesamiento> listPre = new ArrayList<FiltroPreProcesamiento>();
		listPre.add(new FiltroSubstract());
		Procesador preProcesador = new PreProcesador(listPre, seleccionadorPre);

		Procesador pivProcesador = new ProcesadorPIV(new FiltroCorrelacionCruzada(), seleccionador);

		List<FiltroPostProcesamiento> listPost = new ArrayList<FiltroPostProcesamiento>();
		listPost.add(new FiltroTestMedianaNormalizada(2.0, 0.1));
		listPost.add(new FiltroRemplazoVectoresInvalidosPorMediana());
		Procesador postProcesador = new PostProcesador(listPost, seleccionador);

		List<ElementoProcesable> outputPre = preProcesador.procesar(inputImage);
		
		List<ElementoProcesable> outputPIV = pivProcesador.procesar(outputPre);

		List<ElementoProcesable> outputPost = postProcesador.procesar(outputPIV);

		matrixToFile(((MapaVectores) outputPost.get(0)).getMapaVectores(), "C:/Users/Seba/Desktop/PIV/Salidas/salidaFiltrada");

	}

	public static void matrixToFile(double[][] data, String pathname) throws IOException {
		DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance(Locale.US);
		df.applyPattern("+0.0000E00;-0.0000E00");
		FileHandling.writeArrayToFile(data, pathname + ".jvc", df, "");
	}

}
