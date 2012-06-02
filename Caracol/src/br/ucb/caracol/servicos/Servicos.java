
    package br.ucb.caracol.servicos;
    
    import br.ucb.caracol.view.JanelaPrincipal;
    import java.io.*;

    public class Servicos {
        public static String carregaArquivo(String arquivo) throws  IOException{

            File file = new File(arquivo);
            BufferedReader br = new BufferedReader(new FileReader(file));
            StringBuilder saida = new StringBuilder();
            String linha;
            while((linha = br.readLine()) != null)
                saida.append(linha);
            br.close();
            return saida.toString();

        }

        public static void salvaArquivo(String string) throws  IOException{
              BufferedWriter out = new BufferedWriter(new FileWriter(JanelaPrincipal.getPath()));
              out.write(string);
              out.close();
        }
    }
