package automatasact2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 *
 * @author BRYAN FERNANDO RANGEL AGUIRRE 6ISC
 */
public class AutomatasACT2 {

    /**
     * @param args the command line arguments
     */

    public static void main(String[] args) {
        //TABLA DE ERRORES//////
              List lin_err = new ArrayList();//alamaceno la linea del error
              List cod_err = new ArrayList();//almaceno el codigo de error
              List mis_err = new ArrayList();//almaceno mis codigos de error
              List tk_err=new ArrayList();//alamaceno el token erroneo
              cod_err.add("0");
              cod_err.add("1");
              cod_err.add("2");
              cod_err.add("3");
              cod_err.add("4");
              cod_err.add("5");
              cod_err.add("6");
              List desc_err = new ArrayList();//alamaceno la descripcion de cada error
              desc_err.add("ERROR LX0 SE ESTA UTILIZANDO UN CARACTER FUERA DEL ALFABETO EN LA FILA:");
              desc_err.add("ERROR ST0 SE ESTA UTILIZANDO UNA PALABRA RESERVADA EN LA FILA:");
              desc_err.add("ERROR ST1 SINTAXIS INVALIDA EN LA FILA:");
              desc_err.add("ERROR ST2 NO SE CERRARON COMILLAS EN LA FILA:");
              desc_err.add("ERROR ST3 SE ESTA UTILIZANDO UN OPERADOR INVALIDO EN LA FILA:");
              desc_err.add("ERROR SM1 SE ESTA UTILIZANDO UN ID NO DECLARADO EN LA FILA:");
              desc_err.add("ERROR SM2 SE ESTAN UTILIZANDO DIFERENTES TIPOS EN LA FILA");
        //FIN TABLA DE ERRORES//

      boolean bandera=true,pase=false,param=true;
      String palabra1="",palabra2="",palabra3="",aux="";
      String ident="[a-z][a-z0-9]*";
      String tokens="(imprime|lee|si|dura|ciclo)";
      String id="[a-z][a-z0-9]*";
      String consta="([0-9]+|[']([^'])*[']|[0-9]+[\\.][0-9]+)";
      String opera="([+]|[-]|[*]|[/]|[=]|[=][=]|[<][=]|[>][=]|[<]|[>])";
      String agrup="([(]|[)])";
      String letras="abcdefghijklmnopqrstuvwxyz',";//letras de mi alfabeto
      String numeros="0123456789";//numeros de mi alfabeto
      String operadores="/*-+().{}";//operadores
      String logicos="=<>";//operadores logicos
      String Alfabeto=letras+numeros+operadores+logicos;
      String ent="[0-9]+";
      String flot=ent+"[\\.][0-9]+";
      String cade="[']([^'])*[']";//permito todo excepto '
      String tipo="("+ent+"|"+flot+"|[a-z][a-z0-9]*)";
      String comp="([\\s]([+]|[\\-]|[/]|[\\*])[\\s]([0-9]+|([0-9]+[\\.][0-9]+)|[a-z][a-z0-9]*))?";//En operaciones
      String varnum="[a-z][a-z0-9]*[\\s][=][\\s]"+tipo+comp;//maneja operaciones enteras y flotantes
      String varcade="[a-z][a-z0-9]*[\\s][=][\\s]"+"("+cade+"|"+"[a-z][a-z0-9]*"+")"+"([\\s][+][\\s]([']([^'])*[']|[a-z][a-z0-9]*))?";//concatenaciones
      String condiciones="((si|dura)[\\s][(][\\s]([a-z][a-z0-9]*|[0-9]+)[\\s](<|>|==|<>)[\\s]([a-z][a-z0-9]*|[0-9]*)[\\s][)][\\s][{])*";
      String id_ent="[a-z0-9]*[\\s][=][\\s][0-9]+";//variable de tipo entero
      String id_cad ="[a-z][a-z0-9]*[\\s][=][\\s][']([^'])*[']";//variable de tipo cadena
      String id_flot ="[a-z][a-z0-9]*[\\s][=][\\s][0-9]+[\\.][0-9]+";//variable de tipo flotante
      String varc1="([a-z][a-z0-9]*[\\s](<|>|==|<>)[\\s]([0-9]+|[a-z][a-z0-9]*))";//variable con menor y mayor
      String ciclo="((ciclo)[\\s][(][\\s][a-z]"+id_ent+"[\\s]"+"[,][\\s]"+varc1+"[\\s][,][\\s]([\\+][\\+]|[\\-][\\-])[\\s][)][\\s][{])";
      String varia="("+varnum+"|"+varcade+")";
      String operaciones="([\\s]*"+"("+ciclo+"|"+condiciones+"|"+varnum+"|"+varcade+"|}|fin))";//ciclos while, if y for
      String lee="(lee)[\\s][(][\\s][a-z][a-z0-9]*[\\s][)]";//lee
      String imprime="(imprime)[\\s][(][\\s]([a-z][a-z0-9]*|"+ent+"|"+flot+"|"+cade+")[\\s][)]";//expresion para imprime
      String expresion="("+operaciones+"|"+lee+"|"+imprime+")";//expresion de todo

      List datos= new ArrayList();
      List palabras=new ArrayList();
      List tab_id=new ArrayList();
      List tab_tip=new ArrayList();
      List tab_valor=new ArrayList();
      int cont=0;
      File archivo = null;
      FileReader fr = null;
      BufferedReader br = null;
      try {
         // Apertura del fichero y creacion de BufferedReader para poder
         // hacer una lectura comoda (disponer del metodo readLine()).
         archivo = new File ("/home/bryan/NetBeansProjects/AutomatasACT2/texto.txt");
         fr = new FileReader (archivo);
         br = new BufferedReader(fr);

         // Lectura del fichero
         String linea;
         while((linea=br.readLine())!=null){
             if(!"".equals(linea)){datos.add(linea);cont++;}
             else{datos.add(" ");cont++;}}
      }
      catch(Exception e){
         e.printStackTrace();
      }finally{
         // En el finally cerramos el fichero, para asegurarnos
         // que se cierra tanto si todo va bien como si salta
         // una excepcion.
         try{
            if( null != fr ){
               fr.close();
            }
         }catch (Exception e2){
            e2.printStackTrace();
         }
      }
      bandera=true;
      //ANALISIS LEXICO
    for (int i = 0; i < cont; i++) {//para realizarlo con cada palabra
            for (int j = 0; j <datos.get(i).toString().length(); j++) {//para hacerlo con todas las letras de la palabra
                aux=""+datos.get(i).toString().charAt(j);
                if("'".equals(aux)){if(bandera==true){bandera=false;}else{bandera=true;}}
                if(bandera==true){
                for (int k = 0; k <Alfabeto.length(); k++) {//para hacerlo con todo el alfabeto
                        if(!aux.equals(" ")){
                          if(aux.charAt(0)==Alfabeto.charAt(k)){
                          pase=true;//Paro el programa una vez que encuentra la letra en mi alfabeto
                          break;
                        }else{
                          pase=false;}}else{
                          pase=true;break;}  //continuo con la siguiente letra
                }
                if(pase==false){
                  mis_err.add(0);lin_err.add(i+1);tk_err.add(aux.charAt(0));
                }
                }
            }   if(bandera==false){
                  mis_err.add(3);lin_err.add(i+1);tk_err.add("'");}
        }//FIN DE ANALISIS LEXICO

      Pattern patron=Pattern.compile(tokens);
      Pattern patron1=Pattern.compile(id);//comprueba identificadores
      Pattern patron2=Pattern.compile(consta);//comprueba constantes
      Pattern patron3=Pattern.compile(opera);
      Pattern patron4=Pattern.compile(agrup);//para identificar token por token
      Pattern pat=Pattern.compile(ent);
      Pattern pat1=Pattern.compile(flot);
      Pattern pat2=Pattern.compile(cade);
      Pattern yolo=Pattern.compile(ident);

      boolean comilla=false;
    for (int i = 0; i <= (cont-1); i++) {//numero de palabras
        //int tok=0;//cuenta tokens
            for (int j = 0; j <datos.get(i).toString().length(); j++) {//numero de letras
                palabra2=""+datos.get(i).toString().charAt(j);//guardo caracter
                if("'".equals(palabra2)){if(comilla==false){comilla=true;}else{comilla=false;}}
                    if(!" ".equals(palabra2)||" ".equals(palabra2)&&comilla==true){
                      palabra1=palabra1+datos.get(i).toString().charAt(j);
                    }//valido que no sea un espacio
                      if(" ".equals(palabra2)&&comilla==false){
                        palabra3=palabra1;//si es espacio almaceno los caracteres anteriores
                    //tok++;
                    Matcher mtcher=patron.matcher(palabra3);
                    Matcher mtcher1=patron1.matcher(palabra3);
                    Matcher mtcher2=patron2.matcher(palabra3);
                    Matcher mtcher3=patron3.matcher(palabra3);
                    Matcher mtcher4=patron4.matcher(palabra3);
                    if(mtcher.matches()){//compruebo si es palabra reservada
                        System.out.println(palabra3 + " ,Palabra reservada");
                        palabras.add(palabra3);
                        //if(palabras.size()>1){mis_err.add(1);lin_err.add(i+1);tk_err.add(palabras.get(tok-1));}//valido palabras reservadas

                        }else{//valido que es cada cosa
                        if(mtcher1.matches()){
                        System.out.println(palabra3 + " ,Identificador");
                            palabras.add(palabra3);
                    }else{
                         if(mtcher2.matches()){
                        System.out.println(palabra3 + " ,Constante");
                        palabras.add(palabra3);


                    }else{
                         if(mtcher3.matches()){
                        System.out.println(palabra3 + " ,Operador");
                        palabras.add(palabra3);
                    }else{
                             if(mtcher4.matches()){
                        System.out.println(palabra3 + " ,Agrupacion");
                        palabras.add(palabra3);
                    }else{if(!"".equals(palabra3)&&!" ".equals(palabra3)){System.out.println(palabra3 + ",cadena invalida");
                             palabras.add(palabra3);}}
                         }}
                        }}
                        palabra1="";//reinicio la variable con la cadena usada
                }
            }if(comilla==true){comilla=false;}//ya se asigno el error antes de las comillas
                    palabra3=palabra1;//ejecuto lo mismo pero al final de la linea para empezar con la siguiente
                    Matcher mtcher=patron.matcher(palabra3);
                    Matcher mtcher1=patron1.matcher(palabra3);
                    Matcher mtcher2=patron2.matcher(palabra3);
                    Matcher mtcher3=patron3.matcher(palabra3);
                    Matcher mtcher4=patron4.matcher(palabra3);
                    //tok++;
                    if(mtcher.matches()){//compruebo si es palabra reservada
                        System.out.println(palabra3 + " ,Palabra reservada");
                        palabras.add(palabra3);
                        //if(palabras.size()>1){mis_err.add(1);lin_err.add(i+1);tk_err.add(palabras.get(tok-1));}//valido palabras reservadas
                        }else{
                        if(mtcher1.matches()){
                        System.out.println(palabra3 + " ,Identificador");
                        palabras.add(palabra3);
                    }else{
                         if(mtcher2.matches()){
                        System.out.println(palabra3 + " ,Constante");
                        palabras.add(palabra3);
                    }else{
                         if(mtcher3.matches()){
                        System.out.println(palabra3 + " ,Operador");
                        palabras.add(palabra3);
                    }else{
                             if(mtcher4.matches()){
                        System.out.println(palabra3 + " ,Agrupacion");
                        palabras.add(palabra3);
                    }else{if(!"".equals(palabra3)&&!" ".equals(palabra3)){System.out.println(palabra3 + ",cadena invalida");
                    palabras.add(palabra3);}}
                         }}
                        }}
                        palabra1="";
                        if(!" ".equals(datos.get(i).toString())){
                        System.out.println(palabras);
                        Pattern todo=Pattern.compile(expresion);
                        Matcher prueba=todo.matcher(datos.get(i).toString());
                        if(prueba.matches()){//valido que lo que entre al switch sea una cadena que cumpla con mis expresiones regulares
                            if(palabras.size()>1){
                        switch(palabras.size()){
                            case 3:{
                                //compruebo que el token 2 sea un =
                                if(!palabras.get(1).toString().equals("=")){mis_err.add(4);lin_err.add(i+1);tk_err.add(palabras.get(1));}

                                Pattern res=Pattern.compile(tokens);
                                //verifico que lo que se almacena no es una cadena invalida para trabajar
                                Matcher reserv1=res.matcher(palabras.get(2).toString());
                                Matcher idents=patron1.matcher(palabras.get(2).toString());
                                Matcher tk1=patron1.matcher(palabras.get(0).toString());
                                Matcher reserv2=res.matcher(palabras.get(0).toString());
                                if(!tk1.matches()){mis_err.add(2);lin_err.add(i+1);tk_err.add(palabras.get(0));}//valido que el token 1 cumpla con las caracteristicas de un identificador
                                if(reserv2.matches()){mis_err.add(1);lin_err.add(i+1);tk_err.add(palabras.get(0));}
                                if(reserv1.matches()){
                                    mis_err.add(1);lin_err.add(i+1);tk_err.add(palabras.get(2));break;}//verifico que no se este usando una palabra reservada en el token 2
                                if(tab_id.contains(palabras.get(0))&&palabras.get(1).toString().equals("=")){
                                    //seteo el valor si la variable se vuelve a colocar
                                    int ind=tab_id.indexOf(palabras.get(0));
                                    if(idents.matches()){
                                      tab_valor.set(ind,tab_valor.get(tab_id.lastIndexOf(palabras.get(2).toString())));

                                      tab_tip.set(ind,tab_tip.get(tab_id.lastIndexOf(palabras.get(2).toString())));

                                    }
                                    else{
                                        tab_valor.set(ind,palabras.get(2));
                                        Matcher mt=pat.matcher(palabras.get(2).toString());
                                        Matcher mt1=pat1.matcher(palabras.get(2).toString());
                                        Matcher mt2=pat2.matcher(palabras.get(2).toString());
                                        if(mt.matches()){tab_tip.set(ind,"entero");}
                                        else{if(mt1.matches()){tab_tip.set(ind,"flotante");}
                                        else{if(mt2.matches()){tab_tip.set(ind,"cadena");}}}

                                    }

                                }
                                //identificadores
                                Matcher mq=yolo.matcher(palabras.get(2).toString());//verifico que no almacene un identificador en valores
                                if(mq.matches()){
                                    if(tab_id.contains(palabras.get(2).toString())&&palabras.get(1).toString().equals("=")){
                                        //agrego el identificador

                                        if(tk1.matches()){tab_id.add(palabras.get(0));
                                        //agrego el valor de la variable dependiendo su posicion
                                        tab_valor.add(tab_valor.get(tab_id.lastIndexOf(palabras.get(2).toString())));
                                        tab_tip.add(tab_tip.get(tab_id.lastIndexOf(palabras.get(2).toString())));
                                        //agrego el tipo

                                        }else{mis_err.add(2);lin_err.add(i+1);tk_err.add(palabras.get(0));}

                                    }else{mis_err.add(5);lin_err.add(i+1);tk_err.add(palabras.get(2));}
                                    }
                                else{
                                //añado el token 0 a la tabla de identificadores
                                if(tab_id.contains(palabras.get(0))){break;}
                                else{
                                     Matcher tk2=patron2.matcher(palabras.get(2).toString());
                                     Matcher tk3=patron1.matcher(palabras.get(2).toString());//con esto valido que lo segundo que se almacene sea una cadena valida para poder almacenar lo demas
                                     Matcher m=patron1.matcher(palabras.get(0).toString());
                                    if((m.matches()&&tk2.matches()&&"=".equals(palabras.get(1).toString()))||(m.matches()&&tk3.matches()&&"=".equals(palabras.get(1).toString()))){
                                tab_id.add(palabras.get(0));
                                //añado el tipo a la tabla de tipos
                                Matcher mt=pat.matcher(palabra3);
                                Matcher mt1=pat1.matcher(palabra3);
                                Matcher mt2=pat2.matcher(palabras.get(2).toString());
                                if(mt.matches()){tab_tip.add("entero");System.out.println("↑↑↑Declaracion de variable de tipo entero↑↑↑");}
                                else{if(mt1.matches()){tab_tip.add("flotante");System.out.println("↑↑↑Declaracion de variable de tipo flotante↑↑↑");}
                                else{if(mt2.matches()){tab_tip.add("cadena");System.out.println("↑↑↑Declaracion de variable de tipo cadena↑↑↑");}}}
                                //añado el valor
                                tab_valor.add(palabras.get(2));}
                                }}
                                break;

                            }
                            case 4:{
                             //para imprimir
                             Matcher m=patron1.matcher(palabras.get(2).toString());
                             if(!"imprime".equals(palabras.get(0).toString())&&!"lee".equals(palabras.get(0).toString())){
                                 if("lee".equals(palabras.get(0).toString())){if(!m.matches()){
                                     mis_err.add(2);lin_err.add(i+1);tk_err.add(palabras.get(0));
                                 }else{if(!tab_id.contains(palabras.get(2))){
                                     mis_err.add(5);lin_err.add(i+1);tk_err.add(palabras.get(2));}
                                 }
                                 }
                                 mis_err.add(2);lin_err.add(i+1);tk_err.add(palabras.get(0));//valido que el token 1 sea correcto
                             }

                             Matcher mt1=patron1.matcher(palabras.get(2).toString());//identificadores
                             Matcher mt2=patron2.matcher(palabras.get(2).toString());//constantes
                             Matcher mt=patron.matcher(palabras.get(2).toString());
                             if("(".equals(palabras.get(1).toString())){//verifico si los parentesis estan correctos
                             }else{mis_err.add(2);lin_err.add(i+1);tk_err.add(palabras.get(1));}
                             if(")".equals(palabras.get(3).toString())){
                             }else{mis_err.add(2);lin_err.add(i+1);tk_err.add(palabras.get(3));}
                             if(mt.matches()){mis_err.add(1);lin_err.add(i+1);tk_err.add(palabras.get(2));}
                             else{
                             if(mt1.matches()||mt2.matches()){
                             if(mt1.matches()){if(!tab_id.contains(palabras.get(2))){mis_err.add(5);lin_err.add(i+1);tk_err.add(palabras.get(2));}
                             }}
                             else{mis_err.add(2);lin_err.add(i+1);tk_err.add(palabras.get(2));}}
                             break;
                            }
                            case 5:{

                                /*Matcher id1=patron1.matcher(palabras.get(2).toString());//para verificar identificador 1
                                Matcher id2=patron1.matcher(palabras.get(4).toString());//para verificar identificador 2
                                //verifico que el identificador no este declarado
                                if(tab_id.contains(palabras.get(0))){
                                    if(id1.matches()&&id2.matches()){//verifico si es que los dos son identificadores
                                        tab_valor.add(tab_valor.get(tab_id.lastIndexOf(palabras.get(2).toString())));
                                        tab_tip.add(tab_tip.get(tab_id.lastIndexOf(palabras.get(2).toString())));
                                    }
                                //agrego el nuevo valor de la variable
                                //agrego el nuevo tipo de la variable
                                //si se encuentra una variable en la ecuacion, verifico si es que existe
                                //verifico que no se encuentre una cadena y un numero en operacion
                                //verifico que si se esta concatenando que se encuentre solo el simbolo +

                                //break;
                                }
                                else{}*/
                            }
                            case 7:{
                                //Condicionales (si) y (dura)
                                if(!"si".equals(palabras.get(0).toString())&&!"dura".equals(palabras.get(0).toString())){
                                mis_err.add(2);lin_err.add(i+1);tk_err.add(palabras.get(0));}
                                //valido el token 0 sea ciclo

                                if(!"(".equals(palabras.get(1).toString())){
                                mis_err.add(2);lin_err.add(i+1);tk_err.add(palabras.get(1));}
                                //valido el token 1 sea (

                                if(!")".equals(palabras.get(5).toString())){
                                mis_err.add(2);lin_err.add(i+1);tk_err.add(palabras.get(5));}
                                //valido el token 2 sea )

                                if(!"{".equals(palabras.get(6).toString())){
                                mis_err.add(2);lin_err.add(i+1);tk_err.add(palabras.get(6));}
                                //valido el token 1 sea ciclo
                                boolean next=false,next1=false;

                                //PRIMERA VARIABLE
                                Matcher mt1=patron1.matcher(palabras.get(2).toString());//identificadores
                                Matcher mt2=patron2.matcher(palabras.get(2).toString());//constantes
                                Matcher mt=patron.matcher(palabras.get(2).toString());//tokens

                                //valido palabras reservadas
                                if(mt.matches()){
                                mis_err.add(1);lin_err.add(i+1);tk_err.add(palabras.get(2));}//no dejo seguir si es una palabra reservada
                                else{
                                if(mt1.matches()){//verifica que este bien escrito el id
                                    if(!tab_id.contains(palabras.get(2))){mis_err.add(5);lin_err.add(i+1);tk_err.add(palabras.get(2));}//si el id no existe error
                                    else{next=true;}
                                }
                                else{//valido que sea almenos una constante, de lo contrario error de sintaxis
                                if(!mt2.matches()){mis_err.add(2);lin_err.add(i+1);tk_err.add(palabras.get(2));}
                                else{next=true;}
                                }
                                }
                                //FIN DE LA PRIMERA VARIABLE

                                //SEGUNDA VARIABLE
                                Matcher m=patron1.matcher(palabras.get(4).toString());//identificadores
                                Matcher m1=patron2.matcher(palabras.get(4).toString());//constantes
                                Matcher m2=patron.matcher(palabras.get(4).toString());//tokens

                                //valido palabras reservadas
                                if(m2.matches()){
                                mis_err.add(1);lin_err.add(i+1);tk_err.add(palabras.get(4));}//no dejo seguir si es una palabra reservada
                                else{
                                if(m.matches()){//verifica que este bien escrito el id
                                    if(!tab_id.contains(palabras.get(4))){mis_err.add(5);lin_err.add(i+1);tk_err.add(palabras.get(4));}//si el id no existe error semantico
                                    else{next1=true;}
                                }
                                else{//valido que sea almenos una constante, de lo contrario error de sintaxis
                                if(!m1.matches()){mis_err.add(2);lin_err.add(i+1);tk_err.add(palabras.get(4));}else{next1=true;}}}
                                //FIN SEGUNDA VARIABLE

                                //VALIDACION DE TIPOS
                                if(next==true&&next1==true){
                                next=false;next1=false;

                                }

                                break;
                            }
                        }
                        }
                        }else{//si la cadena no cumple con las expresiones regulares mando error sintactico
                            mis_err.add(2);lin_err.add(i+1);tk_err.add(datos.get(i).toString());
                        }
                        //seteo la lista con mis tokens de la linea
                        palabras.clear();}}
                        //imprimo identificadores, su tipo y su valor
                        System.out.println("---IDENTIFICADORES" + tab_id);
                        System.out.println("---TIPO" + tab_tip);
                        System.out.println("---VALOR" + tab_valor);
                        for(int i=0;i<mis_err.size();i++){
                            //impimo mi tabla de errores
                            int x =Integer.parseInt(mis_err.get(i).toString());
                            System.out.println(desc_err.get(x)+""+ lin_err.get(i) + " CON " + tk_err.get(i));
                            }            
        }

    }
