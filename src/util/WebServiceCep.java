package util;

import java.net.URL;
import java.util.Iterator;
import javax.swing.JOptionPane;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class WebServiceCep {

    private String uf = "";
    private String cidade = "";
    private String bairro = "";
    private String tipoLogradouro = "";
    private String logradouro = "";
    private int resultado = 0;
    private String resultadoTxt = "";

    public WebServiceCep(String cep,boolean proxy) {
        if(proxy){
            System.setProperty("http.proxyHost", "172.16.48.1");
            System.setProperty("http.proxyPort", "3128");
            System.setProperty("java.net.socks.username", "aluno-info7");
            System.setProperty("java.net.socks.password", "a12345!");
        }else{
            System.setProperty("http.proxyHost", "");
            System.setProperty("http.proxyPort", "");
            System.setProperty("java.net.socks.username", "");
            System.setProperty("java.net.socks.password", "");
        }
        try {
            URL url = new URL("http://cep.republicavirtual.com.br/web_cep.php?cep=" + cep + "&formato=xml");
            SAXReader leitor = new SAXReader();
            Document document = leitor.read(url);

            Element root = document.getRootElement();

            for (Iterator i = root.elementIterator(); i.hasNext();) {
                Element element = (Element) i.next();

                if (element.getQualifiedName().equals("uf")) {
                    this.uf = element.getText();
                }

                if (element.getQualifiedName().equals("cidade")) {
                    this.cidade = element.getText();
                }

                if (element.getQualifiedName().equals("bairro")) {
                    this.bairro = element.getText();
                }

                if (element.getQualifiedName().equals("tipo_logradouro")) {
                    this.tipoLogradouro = element.getText();
                }

                if (element.getQualifiedName().equals("logradouro")) {
                    this.logradouro = element.getText();
                }

                if (element.getQualifiedName().equals("resultado")) {
                    this.resultado = Integer.parseInt(element.getText());
                }

                if (element.getQualifiedName().equals("resultadoTxt")) {
                    this.resultadoTxt = element.getText();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao obter dados!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String getUf() {
        return uf;
    }

    public String getCidade() {
        return cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public String getTipo_logradouro() {
        return tipoLogradouro;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public int getResultado() {
        return resultado;
    }

    public String getResultado_txt() {
        return resultadoTxt;
    }
}
