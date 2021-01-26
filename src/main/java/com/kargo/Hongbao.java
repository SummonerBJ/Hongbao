/*
 * Created by JFormDesigner on Fri Oct 30 15:46:06 CST 2020
 */

package main.java.com.kargo;

import com.alibaba.fastjson.JSONObject;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import main.java.com.kargo.domain.util.HMACDigest;
import main.java.com.kargo.domain.util.MD5;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Summoner
 */
public class Hongbao extends JFrame {


    private JPanel contentPane;
    private JTextField textField_mid;
    private JTextField textField_tid;
    private JTextField textField_baseUrl;

    public Hongbao() {
        initComponents();
    }

    public static void main(String[] args) {
        new Hongbao().setVisible(true);
    }


    private void initComponents() {
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
        setTitle("Hongbao Tool");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 389);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("mer-id:");
        lblNewLabel.setBounds(103, 72, 61, 16);
        contentPane.add(lblNewLabel);

        textField_mid = new JTextField();
        textField_mid.setText("00062000000");
        textField_mid.setBounds(218, 67, 130, 26);
        contentPane.add(textField_mid);
        textField_mid.setColumns(10);

        JLabel lblTid = new JLabel("amount:");
        lblTid.setBounds(103, 105, 61, 16);
        contentPane.add(lblTid);

        textField_tid = new JTextField();
        textField_tid.setColumns(10);
        textField_tid.setBounds(218, 100, 130, 26);
        contentPane.add(textField_tid);


        JTextArea textArea_result = new JTextArea();
        textArea_result.setWrapStyleWord(true);
        textArea_result.setLineWrap(true);
        textArea_result.setBounds(46, 257, 354, 88);
        contentPane.add(textArea_result);

        textArea_result.setWrapStyleWord(true);
        textArea_result.setLineWrap(true);
        textArea_result.setBounds(46, 257, 354, 88);
        contentPane.add(textArea_result);

        JButton button = new JButton("生成");
        String url = "https://app.kargocard.com/ecode/api/v2/getMallUrl";
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String baseUrl = textField_baseUrl.getText().trim();
                String mid = textField_mid.getText().trim();
                String tid = textField_tid.getText().trim();
                if(mid.equals("")||tid.equals("")||baseUrl.equals("")){
                    JOptionPane.showMessageDialog(Hongbao.this, "参数错误!");
                    return;
                }

                Long timestamp = System.currentTimeMillis();
                String request = "{\"mer-id\":\""+mid+"\",\"mer-ord-id\":\""+timestamp+"\",\"ord-create-date\":\"20190130171739\",\"ord-expire-date\":\"20190130171739\",\"ord-amount\":\""+tid+"\",\"ord-cust\":[{\"cust-email\":\"leongu@kargocard.com\",\"cust-id\":\"201501260000\",\"cust-phone\":\"15201752113\"}]}";

                String httpMethod="post";
                String utc = System.currentTimeMillis()+"";//header中的utc
                String endPoint = "/v2/getMallUrl";
                String  auth_id= "merchant-DigitalRest";
                String secret_key="K@rg0K@rd";

                StringBuffer stringToSign = new StringBuffer();
                stringToSign.append(httpMethod.toUpperCase()).append("\n");//DD33D0D4C7C9C687A0E1DE05B3F1AEB4\n
                stringToSign.append(MD5.md5(request)).append("\n");
                stringToSign.append(utc).append("\n");
                stringToSign.append(endPoint);

                String strToSign = stringToSign.toString();// "POST\nDD33D0D4C7C9C687A0E1DE05B3F1AEB4\n1541530826000\n/api/ver1.1/order";

                String signature = HMACDigest.HMACSHA256(strToSign, secret_key);

                String authorization =  auth_id +":" + signature.toUpperCase();//kc-mer-123456:DD224810429495396A9B2FAAC237B6B01229D610B090B052B4CCC78275251F0F

                System.out.println(authorization);//header中的uthorization

                //组装请求发送：
                Map<String,String> headerMap = new HashMap<>();
                headerMap.put("Content-Type","application/json");
                headerMap.put("Authorization",authorization);
                headerMap.put("kc-utc-date",utc);


                //String url = "https://partner.kargotest.com/ecode/api/v2/getMallUrl";
                HttpRequest httpRequest = HttpRequest
                        .post(url)
                        .trustAllCerts(true)
                        .body(request)
                        .header(headerMap);

                HttpResponse httpResponse = httpRequest.send();
                System.out.println();
                String str = httpResponse.bodyText();
                JSONObject  jsonObject = JSONObject.parseObject(str);
                textArea_result.setText( jsonObject.get("url").toString());

            }
        });
        button.setBounds(165, 188, 117, 29);
        contentPane.add(button);

        JLabel label = new JLabel("结果：");
        label.setBounds(203, 229, 61, 16);
        contentPane.add(label);

        JLabel lblBaseurl = new JLabel("Url:");
        lblBaseurl.setBounds(103, 39, 61, 16);
        contentPane.add(lblBaseurl);

        JLabel lblBaseurl2 = new JLabel(url);
        textField_baseUrl = new JTextField();
        textField_baseUrl.setText(url);
        textField_baseUrl.setColumns(10);
        lblBaseurl2.setBounds(165, 34, 235, 26);

        contentPane.add(lblBaseurl2);

    }

}
