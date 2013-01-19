import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.*;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import javax.swing.*;

import net.sf.json.*;

@SuppressWarnings("serial")
public class Douban extends JFrame implements ActionListener{
	private JTextField i;
	private JButton ok;
	private JTextArea r;
	
	public static void main(String[] args){
		Douban bookGet = new Douban();
	}
	
	Douban(){
		r = new JTextArea();
		r.setEditable(false);
		JScrollPane resultScroll = new JScrollPane(r);
		i=new JTextField("����������");
		i.setBounds(20, 20, 240, 30);
		ok = new JButton("����");
		ok.addActionListener(this);
		ok.setBounds(350, 20, 40, 30);
		JPanel search = new JPanel();
		search.add(i);
		search.add(ok);
		this.add(resultScroll, "Center");
		this.add(search, "North");
		
		int width = 500, height = 300;
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    this.setSize(width, height);
	    this.setLocation(screenSize.width / 2 - width / 2, screenSize.height / 2 - height / 2);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("douban");
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent action) {
		// TODO Auto-generated method stub
		
		 if(action.getSource()==ok){
			 if(i.getText().toString().equals("")) return ;
			 String result = i.getText().trim().toString();
			 String url="https://api.douban.com/v2/book/search?q="+result+"&start=0&count=10";
			 i.setText("");
			 try{
				 DefaultHttpClient client = new DefaultHttpClient();
                 HttpGet get = new HttpGet(url);   
                 HttpResponse response = client.execute(get);
                 result=EntityUtils.toString(response.getEntity()); 
			 }catch(Exception e){	  
				 e.printStackTrace();				 
			 } 
			 
			 if(result.length() == 0){
				 r.setText("û�д���");
			 }
			 else {
				 try{						
						JSONObject jar=JSONObject.fromObject(result);
						JSONArray array=jar.getJSONArray("books"); 
						int resultSize=array.size();    
						
						for(int i = 0;i < resultSize; i++){
							JSONObject obj =  array.getJSONObject(i);
			
							r.append("����: " + obj.getString("title") + "\n");
							
							r.append("����: " + obj.getString("author") + "\n");
							
							r.append("������: " + obj.getString("publisher") + "\n");
							
							r.append("�۸�: " + obj.getString("price") + "\n");	
						}	
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			 }		
	}
}
