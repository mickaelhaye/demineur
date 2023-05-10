package demineur;

import java.util.Scanner;

public class Principal {
	
	static int [][] m_tGrille ;
	static int [][] m_tGrilleDepart ;
	static int m_iHauteurGrille = 0;
	static int m_iLongueurGrille = 0;
	static int m_iNombreDeBombe = 0;
	static int m_iHauteurDemande = 0;
	static int m_iLongueurDemande = 0;
	static boolean	m_bFin = false ;

	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Saisissez la hauteur de la grille : ");
		m_iHauteurGrille = sc.nextInt();
        System.out.print("Saisissez la Longueur de la grille : ");
        m_iLongueurGrille = sc.nextInt();
        System.out.print("Saisissez le nombre de bombe : ");
        m_iNombreDeBombe = sc.nextInt();
		
        m_tGrille = creationGrille(m_iHauteurGrille,m_iLongueurGrille);
		creationDesBombes(m_tGrille, m_iNombreDeBombe);
		for (int i=0; i<m_tGrille.length; i++) {
			for (int j=0; j<m_tGrille[i].length; j++) {
				if(m_tGrille[i][j]!=-1) {
					miseAJourCases(m_tGrille,i,j);
				}
			}
		}
		//affichageGrille(m_tGrille);
		
		m_tGrilleDepart = creationGrille(m_iHauteurGrille,m_iLongueurGrille);
		affichageGrille(m_tGrilleDepart);
		
		while(!m_bFin) {
			m_iHauteurDemande=-1;
			m_iLongueurDemande=-1;
			while(m_iHauteurDemande==-1) {
				System.out.println("Saisir la case à tester en hauteur ");
				m_iHauteurDemande = sc.nextInt();
				if((m_iHauteurDemande<1)||(m_iHauteurDemande>m_iHauteurGrille)){
					m_iHauteurDemande=-1;
					System.out.println("la valeur saisie n'est pas correcte , elle doit être comprise entre 1 et "+m_iHauteurGrille);
				}
			}
			while(m_iLongueurDemande==-1) {
				System.out.println("Saisir la case à tester en Longueur ");
				m_iLongueurDemande = sc.nextInt();
				if((m_iLongueurDemande<1)||(m_iLongueurDemande>m_iLongueurGrille)){
					m_iLongueurDemande=-1;
					System.out.println("la valeur saisie n'est pas correcte , elle doit être comprise entre 1 et "+m_iLongueurGrille);
				}
			}
			m_iHauteurDemande=m_iHauteurDemande-1;
			m_iLongueurDemande=m_iLongueurDemande-1;
			if(m_tGrilleDepart[m_iHauteurDemande][m_iLongueurDemande] != -2 ) {
				System.out.println("case déjà testée");
				System.out.println("");
				continue;
			}
			if(m_tGrille[m_iHauteurDemande][m_iLongueurDemande]==-1){
				System.out.println("BOOM, t' as perdu");
				System.out.println("-----FIN-----");
				m_bFin = true;
				break;
			}
			else if(m_tGrille[m_iHauteurDemande][m_iLongueurDemande]==0){
				System.out.println("Pas de bombe et rien à côté");
				System.out.println("");
				m_tGrilleDepart[m_iHauteurDemande][m_iLongueurDemande]= m_tGrille[m_iHauteurDemande][m_iLongueurDemande];
				majDesAutresCases();
			}
			else {
				System.out.println("Il y a "+ m_tGrille[m_iHauteurDemande][m_iLongueurDemande]+ " bombe à côté.");
				System.out.println("");
				m_tGrilleDepart[m_iHauteurDemande][m_iLongueurDemande]= m_tGrille[m_iHauteurDemande][m_iLongueurDemande];
			}
			affichageGrille(m_tGrilleDepart);
			
			///test si fin
			int iNombreDeCase = m_iLongueurGrille*m_iHauteurGrille;
			int iNombreDeCaseADecouvrir = iNombreDeCase-m_iNombreDeBombe;
			int iNombreDeCaseDecouverte = 0;
			m_bFin = false;
			for (int i=0; i<m_tGrilleDepart.length; i++){
				for (int j=0; j<m_tGrilleDepart[i].length; j++){
					if(m_tGrilleDepart[i][j]!=-2) {
						iNombreDeCaseDecouverte = iNombreDeCaseDecouverte+1;
					}
					if(iNombreDeCaseDecouverte>=iNombreDeCaseADecouvrir) {
						m_bFin = true;
						break;
					}
				}
				if(m_bFin) {
					break;
				}
			}
			if(m_bFin) {
				System.out.println("SUPER, t' as gagné");
				System.out.println("-----FIN-----");
			}
			
		}
		
		affichageGrille(m_tGrille);
		sc.close();
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	public static int[][] creationGrille(int _iHauteur, int _iLongueur){
		int [][] tGrille = new int [_iHauteur][_iLongueur];
		for (int i=0; i<tGrille.length; i++) {
			for (int j=0; j<tGrille[i].length; j++) {
				tGrille[i][j]=-2;
			}
		}
		return tGrille;
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
	public static void affichageGrille(int [][] _tGrille){
		for (int j=0; j<_tGrille[0].length; j++) {
			if(j==0) {
				System.out.print("  |");
			}
			int iVal = j+1;
			if(iVal<10){
				System.out.print(" "+iVal+"|");
			}
			else{
				System.out.print(iVal+"|");
			}
			
		}
		System.out.println("");
		
		for (int i=0; i<_tGrille.length; i++) {
			for (int j=0; j<_tGrille[i].length*3; j++) {
				if(j+1!=_tGrille[i].length*3) {
					System.out.print("-");
				}
				else{
					System.out.println("----");
				}
			}
			int iVal = i+1;
			if(iVal<10) {
				System.out.print(" "+iVal);
			}
			else {
				System.out.print(iVal);
			}
			System.out.print("|");
			for (int j=0; j<_tGrille[i].length; j++) {
				
				if(_tGrille[i][j]<0){
					System.out.print(_tGrille[i][j]+"|");
				}
				else{
					if(_tGrille[i][j]!=0)
					{
						System.out.print(" "+_tGrille[i][j]+"|");
					}
					else
					{
						System.out.print("  "+"|");
					}
				}
			}
			System.out.println("");
		}
		for (int j=0; j<_tGrille[0].length*3; j++) {
			if(j+1!=_tGrille[0].length*3) {
				System.out.print("-");
			}
			else{
				System.out.println("----");
			}
		}
		System.out.println("");
		System.out.println("");
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	public static void creationDesBombes(int [][] _tGrille, int _iNbrBombre) {
		for (int i=0; i<_iNbrBombre; i++){
			int iRandomHaut=1000;
			if(_tGrille.length<1000) {
				iRandomHaut=1000;
			}
			if(_tGrille.length<100) {
				iRandomHaut=100;
			}
			if(_tGrille.length<10) {
				iRandomHaut=10;
			}
			
			int iRandomLong=1000;
			if(_tGrille.length<1000) {
				iRandomLong=1000;
			}
			if(_tGrille.length<100) {
				iRandomLong=100;
			}
			if(_tGrille.length<10) {
				iRandomLong=10;
			}
			
			
			//recherche Position sur la hauteur
			int iPosHaut=-1;
			while ((iPosHaut<1-1) || (iPosHaut>_tGrille.length-1)){
				iPosHaut = (int)(Math.random()*iRandomHaut); 
				int toto=0;
			}
			//recherche Position sur la longueur
			int iPosLong=-1;
			while ((iPosLong<1-1) || (iPosLong>_tGrille[0].length-1)){
				iPosLong = (int)(Math.random()*iRandomLong); 
			}
			if(_tGrille[iPosHaut][iPosLong]==-1) {
				i=i-1;
				continue;
			}
			else {
				_tGrille[iPosHaut][iPosLong]=-1;
			}
		}
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	public static void miseAJourCases(int [][] _tGrille, int _iHauteur, int _iLongueur) {
		int iNombreDeBombe = 0;
		int iHauteurGrille = _tGrille.length;
		int iLongueurGrille = _tGrille[0].length;
		
		if(_iHauteur-1 >= 0) {
			if(_iLongueur-1 >= 0) {
				if(_tGrille[_iHauteur-1][_iLongueur-1]==-1) {
					iNombreDeBombe = iNombreDeBombe+1;
				}
			}
			if(_tGrille[_iHauteur-1][_iLongueur]==-1) {
				iNombreDeBombe = iNombreDeBombe+1;
			}
			if(_iLongueur+1 <= iLongueurGrille-1) {
				if(_tGrille[_iHauteur-1][_iLongueur+1]==-1) {
					iNombreDeBombe = iNombreDeBombe+1;
				}
			}
		}
		
		if(_iLongueur-1 >= 0) {
			if(_tGrille[_iHauteur][_iLongueur-1]==-1) {
				iNombreDeBombe = iNombreDeBombe+1;
			}
		}
		if(_iLongueur+1 <= iLongueurGrille-1) {
			if(_tGrille[_iHauteur][_iLongueur+1]==-1) {
				iNombreDeBombe = iNombreDeBombe+1;
			}
		}
		
		if(_iHauteur+1 <= iHauteurGrille-1) {
			if(_iLongueur-1 >= 0) {
				if(_tGrille[_iHauteur+1][_iLongueur-1]==-1) {
					iNombreDeBombe = iNombreDeBombe+1;
				}
			}
			if(_tGrille[_iHauteur+1][_iLongueur]==-1) {
				iNombreDeBombe = iNombreDeBombe+1;
			}
			if(_iLongueur+1 <= iLongueurGrille-1) {
				if(_tGrille[_iHauteur+1][_iLongueur+1]==-1) {
					iNombreDeBombe = iNombreDeBombe+1;
				}
			}
		}
		
		_tGrille[_iHauteur][_iLongueur] = iNombreDeBombe;
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	public static void majDesAutresCases() {
		int iHauteurGrille = m_tGrille.length-1;
		int iLongueurGrille = m_tGrille[0].length-1;
		boolean bvalChange = true;
		while (bvalChange) {
			bvalChange = false;
			for (int i=0; i<m_tGrilleDepart.length; i++) {
				for (int j=0; j<m_tGrilleDepart[i].length; j++) {
					if(m_tGrilleDepart[i][j] == 0) {
						if (i > 0) {
							if (j > 0) {
								if (m_tGrilleDepart[i - 1][j - 1] == -2) {
									if (m_tGrille[i - 1][j - 1] == 0) {
										m_tGrilleDepart[i - 1][j - 1] = 0;
										bvalChange = true;
										break;
									}
								}
							}
							if (m_tGrilleDepart[i - 1][j] == -2) {
								if (m_tGrille[i - 1][j] == 0) {
									m_tGrilleDepart[i - 1][j] = 0;
									bvalChange = true;
									break;
								}
							}
							if (j < iLongueurGrille) {
								if (m_tGrilleDepart[i - 1][j + 1] == -2) {
									if (m_tGrille[i - 1][j + 1] == 0) {
										m_tGrilleDepart[i - 1][j + 1] = 0;
										bvalChange = true;
										break;
									}
								}
							}
						}
						
						
						if (j > 0) {
							if (m_tGrilleDepart[i][j - 1] == -2) {
								if (m_tGrille[i][j - 1] == 0) {
									m_tGrilleDepart[i][j - 1] = 0;
									bvalChange = true;
									break;
								}
							}
						}
						if (j < iLongueurGrille) {
							if (m_tGrilleDepart[i][j + 1] == -2) {
								if (m_tGrille[i][j + 1] == 0) {
									m_tGrilleDepart[i][j + 1] = 0;
									bvalChange = true;
									break;
								}
							}
						}
					
						
						if (i < iHauteurGrille) {
							if (j > 0) {
								if (m_tGrilleDepart[i + 1][j - 1] == -2) {
									if (m_tGrille[i + 1][j - 1] == 0) {
										m_tGrilleDepart[i + 1][j - 1] = 0;
										bvalChange = true;
										break;
									}
								}
							}
							if (m_tGrilleDepart[i + 1][j] == -2) {
								if (m_tGrille[i + 1][j] == 0) {
									m_tGrilleDepart[i + 1][j] = 0;
									bvalChange = true;
									break;
								}
							}
							if (j < iLongueurGrille) {
								if (m_tGrilleDepart[i + 1][j + 1] == -2) {
									if (m_tGrille[i + 1][j + 1] == 0) {
										m_tGrilleDepart[i + 1][j + 1] = 0;
										bvalChange = true;
										break;
									}
								}
							}
						}
					}
				}
				if(bvalChange) {
					break;
				}
			}
		}
	}
}

