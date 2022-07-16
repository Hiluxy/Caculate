import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Caculator extends JFrame{
	
	private JTextField inputSpace;//화면 구성
	private ArrayList<String> equation = new ArrayList<String>();
	private String num="";
	
	/*계산기 창 만들기*/
	public Caculator() {
		setLayout(null);
		
		//버튼
		inputSpace = new JTextField();//버튼
		inputSpace.setEditable(false); //편집 불가
		inputSpace.setBackground(Color.WHITE);
		inputSpace.setHorizontalAlignment(JTextField.RIGHT);
		inputSpace.setFont(new Font("Arial",Font.BOLD,50));
		inputSpace.setBounds(8,10,270,70); // (8,10)위치에 270*70
		
		//판넬
		JPanel buttonPanel =new JPanel();
		buttonPanel.setLayout(new GridLayout(4,4,10,10));//GridLayout 그리드 형태로 배치. 가로칸*세로칸 상하좌우
		buttonPanel.setBounds(8,90,270,235);//(8,90) 270*235
		
		String button_names[]= {"C","÷","×","=",  "7","8","9","+",   "4","5","6","-",    "1","2","3","0"};
		JButton buttons[]=new JButton[button_names.length];//버튼 배열 만들기
		
		for (int i = 0; i < button_names.length;i++) {
			buttons[i]=new JButton(button_names[i]);
			buttons[i].setFont(new Font("Arial",Font.BOLD,20));
			//버튼 색에 따라 다르게 
			if (button_names[i]=="C") buttons[i].setBackground(Color.RED);
			else if ((i>=4 && i <=6)||(i>=8 && i <=10)||(i>=12 && i<=14)) buttons[i].setBackground(Color.BLACK);
			else buttons[i].setBackground(Color.GRAY);
			buttons[i].setForeground(Color.WHITE); 
			buttons[i].setBorderPainted(false);//테두리 없애기. 선택
			buttons[i].addActionListener(new PadActionListener());
			buttonPanel.add(buttons[i]);
		}
		
		
		add(inputSpace);
		add(buttonPanel);
		
		setTitle("계산기");
		setVisible(true);
		setSize(300,370);
		setLocationRelativeTo(null); //가운데에 창 띄움
		setResizable(false); //사이즈 조절 불가
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //창 닫을때 프로그램도 같이 종료
		
	}
		/*기능 구현*/
		//액션
		class PadActionListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				String operation = e.getActionCommand();//어떤 버튼이 눌렸는지 받아오기
				if (operation.equals("C")) {
					inputSpace.setText("");
				}else if (operation.equals("=")) {
					String result = Double.toString(caculate(inputSpace.getText()));
					inputSpace.setText(""+result);
					num = "";
				}else {
					inputSpace.setText(inputSpace.getText()+e.getActionCommand());
				}
			}
		}
		
		
	private void fullTextParsing(String inputText) {
		equation.clear();
		//계산식의 글자식 하나씩 거쳐가기
		for (int i=0; i<inputText.length();i++) {
			char ch=inputText.charAt(i);
			

			if (ch=='-' | ch=='+'| ch=='×'|ch=='÷') { //연산기호는 ArrayList에 추가
				equation.add(num);
				num="";
				equation.add(ch+"");
			}else { //숫자는 num에 추가
				num=num+ch; 
			}
		}
	}
	
	public double caculate(String inputText) {
		fullTextParsing(inputText);
		
		double prev=0;
		double current=0;
		String mode="";
		
		for (String s :equation) {
			if (s.equals("+")) {
				mode="add";
			}else if (s.equals("-")) {
				mode="sub";
			}else if (s.equals("×")) {
				mode="mul";
			}else if (s.equals("÷")) {
				mode="div";
			}else {
				current = Double.parseDouble(s);
				if(mode=="add") {
					prev+=current; //prev에 값이 갱신되어 저장
				} else if (mode=="sub") {
					prev-=current;
				} else if (mode=="mul") {
					prev*=current;
				} else if (mode=="div") {
					prev/=current;
				} else {
					prev=current;
				}
			}
		}
		
		return prev;
	}
	
	public static void main(String[] args) {
		new Caculator();
	}

}
