package octopus;


/**
 * Parse the textual chord name into a ValidChordName object, which can be latter 
 * on converted in a Chord object.
 * 
 * @see ValidChordName
 * @see ChordNotation
 * @see NoteSymbol
 * @see ChordSymbol
 * @see IntervalSymbol
 * @author Leandro Lesqueves Costalonga
 * @version 1.2
 */

 class ChordNotationInterpreter {
/*
 * Define Nota��o Atual com a gram�tica que repesenta as cifras.
 * Exemplo: O acorde D� S�tima Maior no sistema � representadao por "C7M".
 * O usu�rio pode represent�-lo por "Cmaj7", caso ele queira, alterando a gram�tica.
 */
  public ChordNotation currentChordNotation;

 /*
   * Instancia um objeto ChordSymbolVerifier com a ChordNotation default, ou  seja, a brasileira.
   */
  public ChordNotationInterpreter() {
     currentChordNotation = new ChordNotation();
  }

/*
   * Instancia a currentChordNotation que ser� utilizada para percorrer o aut�mato de
   * valida��o das cifras com a nota��o passada por par�metro.
   * @param notacao Gram�tica que ser� utilizada para percorrer o aut�mato de
   * valida��o das cifras.
   */
  public ChordNotationInterpreter(ChordNotation chordNotation) {
    currentChordNotation = chordNotation;
  }

/**
   * Converts/Parse a string word into a ValidChordName object.
   * @param chordName .
   * @return ValidChordName 
 * @throws ChordNotationException, NoteException 
   */
  public ValidChordName getValidChordName(String chordName) throws ChordNotationException, NoteException {
    int estadoOrigem = 0;
    int ponteiro = 0;
    boolean reconheceuSimbolo = false;
    boolean estadoFinal = false;
    ValidChordName cifraValida = null;
    String fundamental = "";
    Note notaFundamental = null;

    while (true) {
      // Configura��es iniciais
      // O if serve pra definir a saida do while
      if (ponteiro < chordName.length()) {
        reconheceuSimbolo = false;
      }else{
         if (!(cifraValida.containsDuplicatedIntervals())) {
           cifraValida.sortIntervalsVector();
           break;
         }else {
            throw new ChordNotationException(chordName + " Duplicated intervals.", chordName);
          }
       }

      // Estado 1:  Identifica��o de nota
      // Estado 2: Identifica��o de nota seguida de altera��o
      // Precedencia: null
      if (estadoOrigem == 0) {
      // simbNota recebe o objeto NoteSymbol do primeiro caracter da cifra digitada pelo usuario
        NoteSymbol simbNota = currentChordNotation.getNoteSymbol(chordName, ponteiro);
        if (simbNota != null) {
	  fundamental = simbNota.getNote().getSymbol();
	  ponteiro = simbNota.getPosition(ponteiro);
	  NotationalSymbol simbAlteracao = currentChordNotation.getAlterationSymbol(chordName,ponteiro);
	  if (simbAlteracao != null){
	    fundamental += simbAlteracao.getSymbol();
	    notaFundamental = WesternMusicNotes.getNote(fundamental);
	    ponteiro = simbAlteracao.getPosition(ponteiro);
	  }else{
	     notaFundamental = simbNota.getNote();
	   }
	  reconheceuSimbolo = true;
	  estadoOrigem = 2;
          estadoFinal = true;
	  cifraValida = configuraAcordeMaiorBasico(new ValidChordName(new NoteSymbol(fundamental,notaFundamental)));
	  cifraValida.setChordName(chordName);
          // cifraValida contem os atributos notaFundamental, notaBaixo
          // e o array intervalos instanciados. Isso quer dizer que ate aqui foi
          // reconhecido um acorde maior
          continue; //continua em uma nova itera��o do While
        }else{ //lan�a erro
	   //System.out.println("Cifra Inv�lida: Note desconhecida.");
	   reconheceuSimbolo =false;
	   throw new ChordNotationException("Note not found in current notation.", chordName);
	   //break; // Sai do while;
        }
      }

      // Estado 3: Identifica��o de Acorde Diminuto
      //  Precedencia: Estado 2
      if (estadoOrigem == 2) {
	 NotationalSymbol simbDiminuto = currentChordNotation.getDiminishedChordSymbol(chordName, ponteiro);
	 if (simbDiminuto != null) {
	   cifraValida = configuraAcordeDiminuto(cifraValida);
	   ponteiro = simbDiminuto.getPosition(ponteiro);
	   estadoOrigem = 3;
           estadoFinal = true;
	   reconheceuSimbolo = true;
	   continue;
	 }else{
	   reconheceuSimbolo = false;
	 }
      }

      // Estado 33: Identifica��o de suspen��o seguido de numC
      // Estado 34: Identifica��o de in�cio altera��o
      //Precedencia: Estado 3
      if (estadoOrigem == 3 && !estadoFinal) {
        NotationalSymbol simbInicioAlteracao = currentChordNotation.getAlterationBeginningSymbol(chordName, ponteiro);
        NotationalSymbol simbSuspensao = currentChordNotation.getSuspensionSymbol(chordName, ponteiro);
   	if (simbInicioAlteracao != null) {
          ponteiro = simbInicioAlteracao.getPosition(ponteiro);
	  estadoOrigem = 34;
          estadoFinal = false;
	  reconheceuSimbolo = true;
	  continue;
	}else if (simbSuspensao != null){
           ponteiro = simbSuspensao.getPosition(ponteiro);
           IntervalSymbol simbNumC = currentChordNotation.getSimboloIntervaloNumC(chordName,ponteiro);
           if (simbNumC != null) {
             cifraValida = configuraAcordeSuspensao(cifraValida, simbNumC);
             ponteiro = simbNumC.getPosition(ponteiro);
	     estadoOrigem = 33;
             estadoFinal = true;
	     reconheceuSimbolo =true;
           }
         }
      }

      // Estado 35: Identifica��o de nota Adicionada "Add"
      // Estado 36: Identifica��o de Alteracao seguido de numD ou numB
      // Estado 38: Identifica��o de numB ou 7M
      // Precedencia: Estado 34
      if (estadoOrigem == 34 && !estadoFinal){
        NotationalSymbol simbAlteracao = currentChordNotation.getAlterationSymbol(chordName,ponteiro);
        IntervalSymbol simbNumB = currentChordNotation.getSimboloIntervaloNumB(chordName,ponteiro);
       // String setimaMaior = cifra.substring(ponteiro, ponteiro+1) == "7M" ? "7M" : null;
        IntervalSymbol simbSetimaMaior = currentChordNotation.getMajorSeventhSymbol(chordName,ponteiro);
        NotationalSymbol simbNotaAdicionada = currentChordNotation.getAddNoteSymbol(chordName, ponteiro);
        if (simbAlteracao != null) {
          ponteiro = simbAlteracao.getPosition(ponteiro);
	  estadoOrigem = 38;
          estadoFinal = false;
          IntervalSymbol simbNumD = currentChordNotation.getSimboloIntervaloNumD(chordName, ponteiro);
          simbNumB = currentChordNotation.getSimboloIntervaloNumB(chordName,ponteiro);

            //Alberico - tratamento para acordes redundantes
            //Acordes equivalentes A�==A�(#2)==A�(#4)==A�(b5)
            //esse tratamento eh feito na saida do while. foi deixado aqui para uso especifico.
            //a redundancia da quinta (b5) so eh tratada aqui.
          if (simbNumD != null) {
            if (!((simbAlteracao.usedSymbol.equals("#") && simbNumD.usedSymbol.equals("4"))
               || (simbAlteracao.usedSymbol.equals("#") && simbNumD.usedSymbol.equals("2"))
               || (simbAlteracao.usedSymbol.equals("b") && simbNumD.usedSymbol.equals("5")))){
                cifraValida = configuraAcordeDiminutoAlteracao(cifraValida, simbAlteracao, simbNumD);
            }else {
               reconheceuSimbolo = false;
	       throw new ChordNotationException(chordName + " Duplicated intervals. e.g: #2 == b3", chordName);

             }
            ponteiro += simbNumD.getUsedSymbol().length();
          }else if (simbNumB != null) {
             cifraValida = configuraAcordeDiminutoAlteracao(cifraValida, simbAlteracao, simbNumB);
             ponteiro = simbNumB.getPosition(ponteiro);
          }
	  reconheceuSimbolo = true;
	  continue;
        }

        else if (simbNumB != null) {
          ponteiro = simbNumB.getPosition(ponteiro);
	  estadoOrigem = 38;
          estadoFinal = false;
          cifraValida = configuraAcordeDiminutoNumB(cifraValida, simbNumB);
          reconheceuSimbolo = true;
	  continue;
        }
        else if (simbSetimaMaior != null) {
          ponteiro = simbSetimaMaior.getPosition(ponteiro);
	  estadoOrigem = 38;
          estadoFinal = false;
          cifraValida = configuraSetimaMaiorAcordeDiminuto(cifraValida, simbSetimaMaior);
          reconheceuSimbolo = true;
	  continue;
        }
        else if (simbNotaAdicionada != null) {
          ponteiro = simbNotaAdicionada.getPosition(ponteiro);
	  estadoOrigem = 35;
          estadoFinal = false;
         // cifraValida = configuraNotaAdicionadaAcordeDiminuto(cifraValida, simbNotaAdicionada);
          reconheceuSimbolo = true;
	  continue;
        }
        else {return null;}
      }

      // Estado 37: Identifica��o de Alteracao seguido de 11 ou 13
      // Estado 38: Identifica��o dos intervalos 11 ou 13
      // Precedencia: Estado 35
       if (estadoOrigem == 35 && !estadoFinal){
         NotationalSymbol simbAlteracao = currentChordNotation.getAlterationSymbol(chordName, ponteiro);
         IntervalSymbol simbNum = currentChordNotation.getSimboloIntervaloNumB(chordName, ponteiro);
         if (simbAlteracao != null) {
           ponteiro = simbAlteracao.getPosition(ponteiro);
           simbNum = currentChordNotation.getSimboloIntervaloNumB(chordName, ponteiro);
           if ((simbNum != null) && (!simbNum.getUsedSymbol().equals("9"))) {
             ponteiro = simbNum.getPosition(ponteiro);
	     estadoOrigem = 38;
             estadoFinal = false;
             cifraValida = configuraAcordeDiminutoAlteracao(cifraValida, simbAlteracao, simbNum);
             reconheceuSimbolo = true;
	     continue;
           }
         }
         if ((simbNum != null) && (!simbNum.getUsedSymbol().equals("9"))) {
           ponteiro = simbNum.getPosition(ponteiro);
	   estadoOrigem = 38;
           estadoFinal = false;
           cifraValida = configuraNotaAdicionadaAcordeDiminuto(cifraValida, simbNum);
           reconheceuSimbolo = true;
	   continue;
         }
       }

      // Estado 39: Identifica��o de fimAlteracao
      // Precedencia: Estado 38
      if (estadoOrigem == 38 && !estadoFinal){
        NotationalSymbol simbFimAlteracao = currentChordNotation.getAlterationEndingSymbol(chordName, ponteiro);
        if (simbFimAlteracao != null) {
          ponteiro = simbFimAlteracao.getPosition(ponteiro);
          estadoOrigem = 39;
          estadoFinal = true;
	  reconheceuSimbolo = true;
	  continue;
        }
      }

      // Estado 33: Identifica��o de suspensao seguido de NumC
      // Precedencia: Estado 39
      if (estadoOrigem == 39 && !estadoFinal) {
        NotationalSymbol simbSuspensao = currentChordNotation.getSuspensionSymbol(chordName, ponteiro);
   	if (simbSuspensao != null){
          ponteiro = simbSuspensao.getPosition(ponteiro);
          IntervalSymbol simbNumC = currentChordNotation.getSimboloIntervaloNumC(chordName,ponteiro);
          if (simbNumC != null) {
            cifraValida = configuraAcordeSuspensao(cifraValida, simbNumC);
            ponteiro = simbNumC.getPosition(ponteiro);
	    estadoOrigem = 33;
            estadoFinal = true;
	    reconheceuSimbolo = true;
          }
        }
      }

      // Estado 07: Identifica��o de numA
      // Estado 10: Identifica��o de Inicio Alteracao
      // Estado 19: Identifica��o do Interval 7
      // Estado 21: Identifica��o do Interval 7M
      // Precedencia: Estado 2
      if (estadoOrigem == 2) {
        IntervalSymbol simbNumA = currentChordNotation.getSimboloIntervaloNumA(chordName,ponteiro);
        NotationalSymbol simbInicioAlteracao = currentChordNotation.getAlterationBeginningSymbol(chordName,ponteiro);
        IntervalSymbol simbSetimaMenor = currentChordNotation.getMinorSeventhSymbol(chordName,ponteiro);
        IntervalSymbol simbSetimaMaior = currentChordNotation.getMajorSeventhSymbol(chordName,ponteiro);
        if (simbNumA != null) {
          cifraValida = configuraAcordeMenorNumA(cifraValida, simbNumA);
	  ponteiro = simbNumA.getPosition(ponteiro);
	  estadoOrigem = 7;
          estadoFinal = ponteiro < chordName.length() && !(chordName.charAt(ponteiro)=='/') ? false : true;
	  reconheceuSimbolo = true;
	  continue;
        }
        else if (simbInicioAlteracao != null) {
          estadoOrigem = 10;
          estadoFinal = false;
          ponteiro = simbInicioAlteracao.getPosition(ponteiro);
          reconheceuSimbolo = true;
          continue;
        }
        else if (simbSetimaMaior != null) {
          cifraValida = configuraAcordeSetimaMaior(cifraValida);
	  ponteiro = simbSetimaMaior.getPosition(ponteiro);
	  estadoOrigem = 21;
          estadoFinal = ponteiro < chordName.length() && !(chordName.charAt(ponteiro)=='/') ? false : true;
	  reconheceuSimbolo = true;
	  continue;
        }
        else if (simbSetimaMenor != null && simbSetimaMaior == null){
          cifraValida = configuraAcordeSetimaMenor(cifraValida);
	  ponteiro = simbSetimaMenor.getPosition(ponteiro);
	  estadoOrigem = 19;
          estadoFinal = ponteiro < chordName.length() && !(chordName.charAt(ponteiro)=='/') ? false : true;
	  reconheceuSimbolo = true;
	  continue;
        }
      }

      // Estado 8: Identifica��o de simboloJuncaoIntervalo "^" seguido de numB
      // Estado 9: Identifica��o de suspen��o seguido de numC
      // Estado 10: Identifica��o de Inicio Alteracao
      // Precedencia: Estado 7
      if (estadoOrigem == 7) {
        NotationalSymbol simbJuncaoIntervalo = currentChordNotation.getIntervalJunctionSymbol(chordName,ponteiro);
        NotationalSymbol simbSuspensao = currentChordNotation.getSuspensionSymbol(chordName,ponteiro);
        NotationalSymbol simbInicioAlteracao = currentChordNotation.getAlterationBeginningSymbol(chordName,ponteiro);
        if (simbJuncaoIntervalo != null) {
          ponteiro = simbJuncaoIntervalo.getPosition(ponteiro);
          IntervalSymbol simbNumB = currentChordNotation.getSimboloIntervaloNumB(chordName,ponteiro);
          if (simbNumB != null) {
            if (!(cifraValida.containsInterval(IntervalFactory.getInterval(
                                                simbNumB.getUsedSymbol())))){
              cifraValida = configuraAcordeAddNumB(cifraValida, simbNumB);
              //  cifraValida.ordenaVetorIntervalo();
              ponteiro = simbNumB.getPosition(ponteiro);
	      reconheceuSimbolo = true;
	      continue;
            }else {
               reconheceuSimbolo =false;
               throw new ChordNotationException("Duplicated interval.", chordName);
             }
          }
        }else if (simbSuspensao != null) {
           ponteiro = simbSuspensao.getPosition(ponteiro);
           IntervalSymbol simbNumC = currentChordNotation.getSimboloIntervaloNumC(chordName,ponteiro);
           if (simbNumC != null) {
             if (!(cifraValida.containsInterval(IntervalFactory.getInterval(
                                               simbNumC.getUsedSymbol())))){
               cifraValida = configuraAcordeSuspensao(cifraValida, simbNumC);
             //  cifraValida.ordenaVetorIntervalo();
               estadoOrigem = 9;
               estadoFinal = true;
               ponteiro = simbNumC.getPosition(ponteiro);
	       reconheceuSimbolo = true;
	       continue;
             }else {
                reconheceuSimbolo =false;
                throw new ChordNotationException("Duplicated interval.", chordName);
              }
           }
         }else if (simbInicioAlteracao != null) {
           estadoOrigem = 10;
           estadoFinal = false;
           ponteiro = simbInicioAlteracao.getPosition(ponteiro);
           reconheceuSimbolo = true;
           continue;
          }
      }

      // Estado 10: Identifica��o de simboloInicioAteracao
      // Precedencia: Estado 9
      if (estadoOrigem == 9 && !estadoFinal) {
        NotationalSymbol simbInicioAteracao = currentChordNotation.getAlterationBeginningSymbol(chordName,ponteiro);
        if (simbInicioAteracao != null) {
          estadoOrigem = 10;
          estadoFinal = false;
          ponteiro = simbInicioAteracao.getPosition(ponteiro);
          reconheceuSimbolo = true;
          continue;
        }
      }

      // Estado 11: Identifica��o de simbolo alteracao seguido de numD ou numB
      // Estado 12: Identifica��o de nota adicionada seguida de alteracao com numB
      // Estado 13; Identifica��o de nota adicionada seguida numB
      // Precedencia: Estado 10
      if (estadoOrigem == 10 && !estadoFinal) {
        NotationalSymbol simbAlteracao = currentChordNotation.getAlterationSymbol(chordName,ponteiro);
        NotationalSymbol simbNotaAdicionada = currentChordNotation.getAddNoteSymbol(chordName,ponteiro);
        if (simbAlteracao != null) {
          ponteiro = simbAlteracao.getPosition(ponteiro);
          IntervalSymbol simbNumD = currentChordNotation.getSimboloIntervaloNumD(chordName,ponteiro);
          IntervalSymbol simbNumB = currentChordNotation.getSimboloIntervaloNumB(chordName,ponteiro);
	  estadoOrigem = 14;
          estadoFinal = false;
          //eh usado o metodo configuraAcordeDiminutoAlteracao por ser generico.
          //A chordName de entrada eh menor
          if (simbNumD != null) {
            if (!(cifraValida.containsInterval(IntervalFactory.getInterval(
                                                simbAlteracao.getUsedSymbol()
                                              + simbNumD.getUsedSymbol())))){
              cifraValida = configuraAcordeDiminutoAlteracao(cifraValida, simbAlteracao, simbNumD);
              // cifraValida.ordenaVetorIntervalo();
              ponteiro = simbNumD.getPosition(ponteiro);
	      reconheceuSimbolo = true;
	      continue;
            }else {
               reconheceuSimbolo = false;
               throw new ChordNotationException("Duplicated interval.", chordName);
             }
          }
          else if (simbNumB != null) {
            if (!(cifraValida.containsInterval(IntervalFactory.getInterval(
                                                simbAlteracao.getUsedSymbol()
                                              + simbNumB.getUsedSymbol())))){
              cifraValida = configuraAcordeDiminutoAlteracao(cifraValida, simbAlteracao, simbNumB);
              // cifraValida.ordenaVetorIntervalo();
              ponteiro = simbNumB.getPosition(ponteiro);
	      reconheceuSimbolo = true;
	      continue;
            }else {
               reconheceuSimbolo = false;
               throw new ChordNotationException("Duplicated interval.", chordName);
             }
          }
        }
        else if (simbNotaAdicionada != null) {
          ponteiro = simbNotaAdicionada.getPosition(ponteiro);
          simbAlteracao = currentChordNotation.getAlterationSymbol(chordName,ponteiro);
          IntervalSymbol simbNumB = currentChordNotation.getSimboloIntervaloNumB(chordName,ponteiro);
          if (simbAlteracao != null) {
            ponteiro = simbAlteracao.getPosition(ponteiro);
            simbNumB = currentChordNotation.getSimboloIntervaloNumB(chordName,ponteiro);
            //eh usado o metodo configuraAcordeDiminutoAlteracao por ser generico.
            //A chordName de entrada eh menor
            if (simbNumB != null) {
              if (!(cifraValida.containsInterval(IntervalFactory.getInterval(
                                                simbAlteracao.getUsedSymbol()
                                              + simbNumB.getUsedSymbol())))){
                cifraValida = configuraAcordeDiminutoAlteracao(cifraValida, simbAlteracao, simbNumB);
                // cifraValida.ordenaVetorIntervalo();
                ponteiro = simbNumB.getPosition(ponteiro);
                estadoOrigem = 14;
                estadoFinal = false;
	        reconheceuSimbolo = true;
	        continue;
              }else {
                 reconheceuSimbolo = false;
                 throw new ChordNotationException("Duplicated interval.", chordName);
               }
            }
          }else if (simbNumB != null) {
              ponteiro = simbNumB.getPosition(ponteiro);
              if (!(cifraValida.containsInterval(IntervalFactory.getInterval(
                                                simbNumB.getUsedSymbol())))){
                cifraValida = configuraAcordeAddNumB(cifraValida, simbNumB);
                // cifraValida.ordenaVetorIntervalo();
                estadoOrigem = 14;
                estadoFinal = false;
                reconheceuSimbolo = true;
                continue;
              }else {
                 reconheceuSimbolo = false;
                 throw new ChordNotationException("Duplicated interval.",chordName);
               }
          }
        }
      }

      // Estado 15: Identifica��o de simbolo Juncao Interval "^"
      // Estado 16: Identifica��o de simbFimAlteracao
      // Precedencia: Estado 14
      if (estadoOrigem == 14 && !estadoFinal) {
        NotationalSymbol simbFimAlteracao = currentChordNotation.getAlterationEndingSymbol(chordName,ponteiro);
        NotationalSymbol simbJuncaoIntervalo = currentChordNotation.getIntervalJunctionSymbol(chordName,ponteiro);
        if (simbFimAlteracao != null) {
          ponteiro = simbFimAlteracao.getPosition(ponteiro);
          estadoOrigem = 16;
          estadoFinal = ponteiro < chordName.length() && !(chordName.charAt(ponteiro)=='/') ? false : true;
          reconheceuSimbolo = true;
          continue;
        }
        else if (simbJuncaoIntervalo != null) {
          ponteiro = simbJuncaoIntervalo.getPosition(ponteiro);
          estadoOrigem = 15;
          estadoFinal = false;
          reconheceuSimbolo = true;
          continue;
        }
      }

      // Estado 14: Identifica��o de nota adicionada com numB
      // Estado 17: Identifica��o de nota adicionada com altera��o
      // Precedencia: Estado 15
      if (estadoOrigem == 15 && !estadoFinal) {
        NotationalSymbol simbNotaAdicionada = currentChordNotation.getAddNoteSymbol(chordName,ponteiro);
        if (simbNotaAdicionada != null) {
          ponteiro = simbNotaAdicionada.getPosition(ponteiro);
        }else {
          reconheceuSimbolo = false;
          continue;
        }
        IntervalSymbol simbNumB = currentChordNotation.getSimboloIntervaloNumB(chordName,ponteiro);
        NotationalSymbol simbAlteracao = currentChordNotation.getAlterationSymbol(chordName,ponteiro);
        if (simbNumB != null) {
          if (!(cifraValida.containsInterval(IntervalFactory.getInterval(
                                                simbNumB.getUsedSymbol())))){
            cifraValida = configuraAcordeAddNumB(cifraValida, simbNumB);
            // cifraValida.ordenaVetorIntervalo();
            ponteiro = simbNumB.getPosition(ponteiro);
            estadoOrigem = 14;
            estadoFinal = false;
            reconheceuSimbolo = true;
            continue;
          }else {
             reconheceuSimbolo = false;
             throw new ChordNotationException("Duplicated interval.",chordName);
           }
        }
        else if (simbAlteracao != null) {
          ponteiro = simbAlteracao.getPosition(ponteiro);
          simbNumB = currentChordNotation.getSimboloIntervaloNumB(chordName,ponteiro);
          if (simbNumB != null) {
            if (!(cifraValida.containsInterval(IntervalFactory.getInterval(
                                                simbAlteracao.getUsedSymbol()
                                              + simbNumB.getUsedSymbol())))){
              ponteiro = simbNumB.getPosition(ponteiro);
              //cifraValida = configuraAcordeAddNumB(cifraValida, simbNumB);
              // cifraValida.ordenaVetorIntervalo();
              //eh usado o metodo configuraAcordeDiminutoAlteracao por ser generico.
              //A chordName de entrada eh menor
              cifraValida = configuraAcordeDiminutoAlteracao(cifraValida, simbAlteracao, simbNumB);
              estadoOrigem = 14;
              estadoFinal = false;
              reconheceuSimbolo = true;
              continue;
            }else {
               reconheceuSimbolo = false;
               throw new ChordNotationException("Duplicated interval.",chordName);
             }
          }
        }
      }

      // Estado 18: Identifica��o do sus com numC {2, 4, 9, 11}
      // Precedencia: Estado 16
      if (estadoOrigem == 16 && !estadoFinal) {
        NotationalSymbol simbSuspensao = currentChordNotation.getSuspensionSymbol(chordName, ponteiro);
        if (simbSuspensao != null) {
           ponteiro = simbSuspensao.getPosition(ponteiro);
           IntervalSymbol simbNumC = currentChordNotation.getSimboloIntervaloNumC(chordName,ponteiro);
           if (simbNumC != null) {
             if (!(cifraValida.containsInterval(IntervalFactory.getInterval(
                                                simbNumC.getUsedSymbol())))){
               cifraValida = configuraAcordeSuspensao(cifraValida, simbNumC);
              //  cifraValida.ordenaVetorIntervalo();
               estadoOrigem = 18;
               estadoFinal = true;
               ponteiro = simbNumC.getPosition(ponteiro);
               reconheceuSimbolo = true;
	       continue;
             }else {
                reconheceuSimbolo =false;
                throw new ChordNotationException("Duplicated interval.", chordName);
              }
           }
        }
      }

      // Estado 20: Identificacao de sus com numC {2, 4, 9, 11}
      // Estado 21: Identifica��o do acorde Maior com setima maior
      // Precedencia: Estado 19
      if (estadoOrigem == 19 && !estadoFinal) {
        NotationalSymbol simbInicioAlteracao = currentChordNotation.getAlterationBeginningSymbol(chordName,ponteiro);
        NotationalSymbol simbJuncaoIntervalo = currentChordNotation.getIntervalJunctionSymbol(chordName,ponteiro);
        NotationalSymbol simbSuspensao = currentChordNotation.getSuspensionSymbol(chordName,ponteiro);
        if (simbInicioAlteracao != null) {
          ponteiro = simbInicioAlteracao.getPosition(ponteiro);
	  estadoOrigem = 23;
          estadoFinal = false;
        }else if (simbJuncaoIntervalo != null) {
           ponteiro = simbJuncaoIntervalo.getPosition(ponteiro);
           IntervalSymbol simbNumA = currentChordNotation.getSimboloIntervaloNumA(chordName,ponteiro);
           if (simbNumA != null) {
             cifraValida = configuraAcordeMenorNumANumB(cifraValida, simbNumA);
             // cifraValida.ordenaVetorIntervalo();
             ponteiro = simbNumA.getPosition(ponteiro);
	     estadoOrigem = 22;
             estadoFinal = true;
	     reconheceuSimbolo = true;
	     continue;
           }
         }else if (simbSuspensao != null) {
            ponteiro = simbSuspensao.getPosition(ponteiro);
            IntervalSymbol simbNumC = currentChordNotation.getSimboloIntervaloNumC(chordName,ponteiro);
            if (simbNumC != null) {
              if (!(cifraValida.containsInterval(IntervalFactory.getInterval(
                                                 simbNumC.getUsedSymbol())))){
                cifraValida = configuraAcordeSuspensao(cifraValida, simbNumC);
               //  cifraValida.ordenaVetorIntervalo();
                estadoOrigem = 20;
                estadoFinal = true;
                ponteiro = simbNumC.getPosition(ponteiro);
	        reconheceuSimbolo = true;
	        continue;
              }else {
                 reconheceuSimbolo =false;
                 throw new ChordNotationException("Duplicated interval.", chordName);
               }
            }
          }
      }

      // Estado 22: Identificacao de juncao de intervalo seguido de numA OU inicio Alteracao
      // Precedencia: Estado 21
      if (estadoOrigem == 21 && !estadoFinal) {
        NotationalSymbol simbSuspensao = currentChordNotation.getSuspensionSymbol(chordName, ponteiro);
        NotationalSymbol simbJuncaoIntervalo = currentChordNotation.getIntervalJunctionSymbol(chordName, ponteiro);
        NotationalSymbol simbInicioAlteracao = currentChordNotation.getAlterationBeginningSymbol(chordName, ponteiro);
        if (simbSuspensao != null) {
            ponteiro = simbSuspensao.getPosition(ponteiro);
            IntervalSymbol simbNumC = currentChordNotation.getSimboloIntervaloNumC(chordName,ponteiro);
            if (simbNumC != null) {
              if (!(cifraValida.containsInterval(IntervalFactory.getInterval(
                                                 simbNumC.getUsedSymbol())))){
                cifraValida = configuraAcordeSuspensao(cifraValida, simbNumC);
               //  cifraValida.ordenaVetorIntervalo();
                estadoOrigem = 20;
                estadoFinal = true;
                ponteiro = simbNumC.getPosition(ponteiro);
	        reconheceuSimbolo = true;
	        continue;
              }else {
                 reconheceuSimbolo =false;
                 throw new ChordNotationException("Duplicated interval.", chordName);
               }
            }
        }else if (simbJuncaoIntervalo != null) {
           ponteiro = simbJuncaoIntervalo.getPosition(ponteiro);
           IntervalSymbol simbNumA = currentChordNotation.getSimboloIntervaloNumA(chordName, ponteiro);
           if (simbNumA != null) {
             cifraValida = configuraAcordeMenorNumANumB(cifraValida, simbNumA);
             // cifraValida.ordenaVetorIntervalo();
             ponteiro = simbNumA.getPosition(ponteiro);
	     estadoOrigem = 22;
             estadoFinal = true;
	     reconheceuSimbolo = true;
	     continue;
           }
         }else if (simbInicioAlteracao != null) {
            ponteiro = simbInicioAlteracao.getPosition(ponteiro);
	    estadoOrigem = 23;
            estadoFinal = false;
	    reconheceuSimbolo = true;
            continue;
          }
      }

      // Estado 20: Identificacao de sus com numC {2, 4, 9, 11}
      // Estado 23: Identificacao de inicio alteracao
      // Precedencia: Estado 22
      if (estadoOrigem == 22 && !estadoFinal) {
        NotationalSymbol simbSuspensao = currentChordNotation.getSuspensionSymbol(chordName, ponteiro);
        NotationalSymbol simbInicioAlteracao = currentChordNotation.getAlterationBeginningSymbol(chordName, ponteiro);
        if (simbSuspensao != null) {
           ponteiro = simbSuspensao.getPosition(ponteiro);
           IntervalSymbol simbNumC = currentChordNotation.getSimboloIntervaloNumC(chordName,ponteiro);
           if (simbNumC != null) {
             if (!(cifraValida.containsInterval(IntervalFactory.getInterval(
                                                 simbNumC.getUsedSymbol())))){
               cifraValida = configuraAcordeSuspensao(cifraValida, simbNumC);
               //  cifraValida.ordenaVetorIntervalo();
               estadoOrigem = 20;
               estadoFinal = true;
               ponteiro = simbNumC.getPosition(ponteiro);
	       reconheceuSimbolo = true;
	       continue;
             }else {
                reconheceuSimbolo =false;
                throw new ChordNotationException("Duplicated interval.", chordName);
              }
           }
        }else if (simbInicioAlteracao != null) {
           ponteiro = simbInicioAlteracao.getPosition(ponteiro);
	   estadoOrigem = 23;
           estadoFinal = false;
	   reconheceuSimbolo = true;
	   continue;
         }
      }

      // Estado 24: Identificacao de nota adicionada seguido de 11 ou seguida de 13
      // Estado 25: Identifica��o de Alteracao seguido de NumB ou seguido de NumD
      // Estado 27: Identifica��o do intervalo Nona
      // Precedencia: Estado 53
      if (estadoOrigem == 23 && !estadoFinal) {
        NotationalSymbol simbAlteracao = currentChordNotation.getAlterationSymbol(chordName,ponteiro);
        IntervalSymbol simbIntervaloNona = currentChordNotation.getMajorNinthSymbol(chordName,ponteiro);
        NotationalSymbol simbNotaAdicionada = currentChordNotation.getAddNoteSymbol(chordName,ponteiro);
        if (simbAlteracao != null) {
          ponteiro = simbAlteracao.getPosition(ponteiro);
          IntervalSymbol simbNumB = currentChordNotation.getSimboloIntervaloNumB(chordName,ponteiro);
          IntervalSymbol simbNumD = currentChordNotation.getSimboloIntervaloNumD(chordName,ponteiro);
          if ((simbNumB != null) || (simbNumD != null)) {
            cifraValida = configuraAcordeDiminutoAlteracao(cifraValida, simbAlteracao, (simbNumB != null ? simbNumB : simbNumD));
            // cifraValida.ordenaVetorIntervalo();
            ponteiro = simbNumB != null ? simbNumB.getPosition(ponteiro)
                                        : simbNumD.getPosition(ponteiro);
	    estadoOrigem = 27;
            estadoFinal = false;
	    reconheceuSimbolo = true;
	    continue;
          }
        }else if (simbIntervaloNona != null) {
           cifraValida.addMajorNinth();
           ponteiro = simbIntervaloNona.getPosition(ponteiro);
	   estadoOrigem = 27;
           estadoFinal = false;
	   reconheceuSimbolo = true;
           continue;
         }else if (simbNotaAdicionada != null) {
            ponteiro = simbNotaAdicionada.getPosition(ponteiro);
	    estadoOrigem = 24;
            estadoFinal = false;
	    reconheceuSimbolo = true;
            continue;
        }
      }

      // Estado 26: Identifica��o de altera��o seguido de 11 ou 13
      // Estado 27: Identifica��o de Interval de 11 ou 13
      // Precedencia: Estado 24
      if (estadoOrigem == 24 && !estadoFinal) {
        NotationalSymbol simbAlteracao = currentChordNotation.getAlterationSymbol(chordName,ponteiro);
        IntervalSymbol simbIntervaloDecimaPrimeiraJusta =
                       currentChordNotation.getPerfect11th(chordName,ponteiro);
        IntervalSymbol simbIntervaloDecimaTerceiraMaior =
                       currentChordNotation.getMajor13thSymbol(chordName,ponteiro);
        if (simbAlteracao != null) {
          ponteiro = simbAlteracao.getPosition(ponteiro);
          simbIntervaloDecimaPrimeiraJusta =
                         currentChordNotation.getPerfect11th(chordName,ponteiro);
          simbIntervaloDecimaTerceiraMaior =
                         currentChordNotation.getMajor13thSymbol(chordName,ponteiro);
          if ((simbIntervaloDecimaPrimeiraJusta != null) || (simbIntervaloDecimaTerceiraMaior != null)) {
            cifraValida = configuraAcordeDiminutoAlteracao(cifraValida, simbAlteracao,
                          (simbIntervaloDecimaPrimeiraJusta != null
                          ? simbIntervaloDecimaPrimeiraJusta : simbIntervaloDecimaTerceiraMaior));
            // cifraValida.ordenaVetorIntervalo();
            ponteiro = simbIntervaloDecimaPrimeiraJusta != null
                          ? simbIntervaloDecimaPrimeiraJusta.getPosition(ponteiro)
                          : simbIntervaloDecimaTerceiraMaior.getPosition(ponteiro);
	    estadoOrigem = 27;
            estadoFinal = false;
	    reconheceuSimbolo = true;
	    continue;
          }
        }else if ((simbIntervaloDecimaPrimeiraJusta != null) || (simbIntervaloDecimaTerceiraMaior != null)) {
           cifraValida = configuraAcordeAddNumB(cifraValida,
                         (simbIntervaloDecimaPrimeiraJusta != null
                         ? simbIntervaloDecimaPrimeiraJusta : simbIntervaloDecimaTerceiraMaior));
           // cifraValida.ordenaVetorIntervalo();
           ponteiro = simbIntervaloDecimaPrimeiraJusta != null
                         ? simbIntervaloDecimaPrimeiraJusta.getPosition(ponteiro)
                         : simbIntervaloDecimaTerceiraMaior.getPosition(ponteiro);
	   estadoOrigem = 27;
           estadoFinal = false;
	   reconheceuSimbolo = true;
	   continue;
         }
      }

      // Estado 29: Identifica��o de simbolo de Juncao Interval "^" seguido de numB
      // Estado 30: Identifica��o de simbolo de Juncao Interval "^" seguido de nota adicionada
      // Estado 31: Identifica��o de fimAlteracao
      // Precedencia: Estado 27
      if (estadoOrigem == 27 && !estadoFinal){
        NotationalSymbol simbFimAlteracao = currentChordNotation.getAlterationEndingSymbol(chordName, ponteiro);
        NotationalSymbol simbJuncaoIntervalo = currentChordNotation.getIntervalJunctionSymbol(chordName, ponteiro);
        if (simbFimAlteracao != null) {
          ponteiro = simbFimAlteracao.getPosition(ponteiro);
          estadoOrigem = 31;
          estadoFinal = ponteiro < chordName.length() && !(chordName.charAt(ponteiro)=='/') ? false : true;
	  reconheceuSimbolo = true;
	  continue;
        }else if (simbJuncaoIntervalo != null) {
           ponteiro = simbJuncaoIntervalo.getPosition(ponteiro);
           NotationalSymbol simbNotaAdicionada = currentChordNotation.getAddNoteSymbol(chordName, ponteiro);
           NotationalSymbol simbAlteracao = currentChordNotation.getAlterationSymbol(chordName, ponteiro);
           IntervalSymbol simbNumB = currentChordNotation.getSimboloIntervaloNumB(chordName, ponteiro);
           if (simbNotaAdicionada != null) {
             ponteiro = simbNotaAdicionada.getPosition(ponteiro);
             simbNumB = currentChordNotation.getSimboloIntervaloNumB(chordName, ponteiro);
             simbAlteracao = currentChordNotation.getAlterationSymbol(chordName, ponteiro);
             if (simbNumB != null) {
               if (!(cifraValida.containsInterval(IntervalFactory.getInterval(
                                                simbNumB.getUsedSymbol())))){
                cifraValida = configuraAcordeAddNumB(cifraValida, simbNumB);
                ponteiro = simbNumB.getPosition(ponteiro);
                // cifraValida.ordenaVetorIntervalo();
              }else {
                 reconheceuSimbolo = false;
                 throw new ChordNotationException(chordName + "Duplicated interval.",chordName);
               }
             }else if (simbAlteracao != null) {
                ponteiro = simbAlteracao.getPosition(ponteiro);
                simbNumB = currentChordNotation.getSimboloIntervaloNumB(chordName, ponteiro);
                if (simbNumB != null) {
                  if (!(cifraValida.containsInterval(IntervalFactory.getInterval(
                                                simbNumB.getUsedSymbol())))){
                    cifraValida = configuraAcordeDiminutoAlteracao(cifraValida, simbAlteracao, simbNumB);
                    ponteiro = simbNumB.getPosition(ponteiro);
                  }else {
                     reconheceuSimbolo = false;
                     throw new ChordNotationException(chordName + " Duplicated interval.",chordName);
                   }
                }else {
                   reconheceuSimbolo = false;
                   throw new ChordNotationException(chordName + " error",chordName);
                 }
              }else {
                 reconheceuSimbolo = false;
                 throw new ChordNotationException(chordName + " error",chordName);
               }
             //estadoOrigem = 27;  //linha desnecessaria. Apenas para facilitar a manutencao.
	     //reconheceuSimbolo = true;
	     //continue;
           }else if (simbAlteracao != null){
              ponteiro = simbAlteracao.getPosition(ponteiro);
              simbNumB = currentChordNotation.getSimboloIntervaloNumB(chordName, ponteiro);
              if (simbNumB != null) {
                cifraValida = configuraAcordeDiminutoAlteracao(cifraValida, simbAlteracao, simbNumB);
                ponteiro = simbNumB.getPosition(ponteiro);
                //estadoOrigem = 27;  //linha desnecessaria. Apenas para facilitar a manutencao.
	        //reconheceuSimbolo = true;
	       // continue;
              }else {
                 reconheceuSimbolo = false;
                 throw new ChordNotationException(chordName + " error ",chordName);
               }

           }else if (simbNumB != null) {
              if (!(cifraValida.containsInterval(IntervalFactory.getInterval(
                                                simbNumB.getUsedSymbol())))){
                cifraValida = configuraAcordeAddNumB(cifraValida, simbNumB);
                ponteiro = simbNumB.getPosition(ponteiro);
                // cifraValida.ordenaVetorIntervalo();
              }else {
                 reconheceuSimbolo = false;
                 throw new ChordNotationException(chordName + " Duplicated interval.",chordName);
               }
           }else {
              reconheceuSimbolo = false;
              throw new ChordNotationException(chordName + " error",chordName);
            }
           estadoOrigem = 27;  //linha desnecessaria. Apenas para facilitar a manutencao.
           estadoFinal = false;
	   reconheceuSimbolo = true;
	   continue;
         }
      }

      // Estado 31: Identifica��o de suspensao seguido de numC {2, 4, 9, 11}
      // Precedencia: Estado 27
      if (estadoOrigem == 31 && !estadoFinal) {
        NotationalSymbol simbSuspensao = currentChordNotation.getSuspensionSymbol(chordName, ponteiro);
        if (simbSuspensao != null) {
           ponteiro = simbSuspensao.getPosition(ponteiro);
           IntervalSymbol simbNumC = currentChordNotation.getSimboloIntervaloNumC(chordName,ponteiro);
           if (simbNumC != null) {
             if (!(cifraValida.containsInterval(IntervalFactory.getInterval(
                                                simbNumC.getUsedSymbol())))){
               cifraValida = configuraAcordeSuspensao(cifraValida, simbNumC);
              //  cifraValida.ordenaVetorIntervalo();
               estadoOrigem = 32;
               estadoFinal = true;
               ponteiro = simbNumC.getPosition(ponteiro);
               reconheceuSimbolo = true;
	       continue;
             }else {
                reconheceuSimbolo =false;
                throw new ChordNotationException("Duplicated interval.", chordName);
              }
           }
        }
      }



      // Estado 4: Identifica��o do Acorde Menor
      // Precedencia: Estado 2
      if (estadoOrigem == 2) {
        IntervalSymbol simbTercaMenor = currentChordNotation.getMinorThirdSymbol(chordName, ponteiro);
        if (simbTercaMenor != null) {
	  cifraValida = configuraAcordeMenorBasico(cifraValida);
	  ponteiro = simbTercaMenor.getPosition(ponteiro);
	  estadoOrigem = 4;
          estadoFinal = ponteiro < chordName.length() && !(chordName.charAt(ponteiro)=='/') ? false : true;
	  reconheceuSimbolo = true;
	  continue;
 	} else {reconheceuSimbolo = false;}
      }

      // Estado 40: Identifica��o de numA
      // Estado 42: Identifica��o de Inicio Alteracao
      // Estado 50: Identifica��o do intervalo 7
      // Estado 51: Identifica��o do intervalo 7M
      // Precedencia: Estado 4
      if (estadoOrigem == 4 && !estadoFinal) {
        IntervalSymbol simbNumA = currentChordNotation.getSimboloIntervaloNumA(chordName,ponteiro);
        NotationalSymbol simbInicioAlteracao = currentChordNotation.getAlterationBeginningSymbol(chordName,ponteiro);
        IntervalSymbol simbSetimaMenor = currentChordNotation.getMinorSeventhSymbol(chordName,ponteiro);
        IntervalSymbol simbSetimaMaior = currentChordNotation.getMajorSeventhSymbol(chordName,ponteiro);
        if (simbNumA != null) {
          cifraValida = configuraAcordeMenorNumA(cifraValida, simbNumA);
	  ponteiro = simbNumA.getPosition(ponteiro);
	  estadoOrigem = 40;
          estadoFinal = true;
	  reconheceuSimbolo = true;
	  continue;
        }
        else if (simbInicioAlteracao != null) {
          estadoOrigem = 42;
          estadoFinal = ponteiro < chordName.length() && !(chordName.charAt(ponteiro)=='/') ? false : true;
          ponteiro = simbInicioAlteracao.getPosition(ponteiro);
          reconheceuSimbolo = true;
          continue;
        }
        else if (simbSetimaMaior != null) {
          cifraValida = configuraAcordeSetimaMaior(cifraValida);
	  ponteiro = simbSetimaMaior.getPosition(ponteiro);
	  estadoOrigem = 51;
          estadoFinal = true;
	  reconheceuSimbolo = true;
	  continue;
        }
        else if (simbSetimaMenor != null && simbSetimaMaior == null){
          cifraValida = configuraAcordeSetimaMenor(cifraValida);
	  ponteiro = simbSetimaMenor.getPosition(ponteiro);
	  estadoOrigem = 50;
          estadoFinal = ponteiro < chordName.length() && !(chordName.charAt(ponteiro)=='/') ? false : true;
	  reconheceuSimbolo = true;
	  continue;
        }
      }

      // Estado 41: Identifica��o de simboloJuncaoIntervalo "^" seguido de numB
      // Precedencia: Estado 40
      if (estadoOrigem == 40 && !estadoFinal) {
        NotationalSymbol simbJuncaoIntervalo = currentChordNotation.getIntervalJunctionSymbol(chordName,ponteiro);
        if (simbJuncaoIntervalo != null) {
          ponteiro = simbJuncaoIntervalo.getPosition(ponteiro);
        }
        IntervalSymbol simbNumB = currentChordNotation.getSimboloIntervaloNumB(chordName,ponteiro);
        if ((simbNumB != null) && (simbJuncaoIntervalo != null)) {
          if (!(cifraValida.containsInterval(IntervalFactory.getInterval(
                                                simbNumB.getUsedSymbol())))){
            cifraValida = configuraAcordeAddNumB(cifraValida, simbNumB);
          //  cifraValida.ordenaVetorIntervalo();
            ponteiro = simbNumB.getPosition(ponteiro);
	    reconheceuSimbolo = true;
	    continue;
          }else {
             reconheceuSimbolo =false;
             throw new ChordNotationException("Duplicated interval.", chordName);
           }
        }
        else {
          NotationalSymbol simbInicioAlteracao = currentChordNotation.getAlterationBeginningSymbol(chordName,ponteiro);
          if (simbInicioAlteracao != null) {
            estadoOrigem = 42;
            estadoFinal = false;
            ponteiro = simbInicioAlteracao.getPosition(ponteiro);
            reconheceuSimbolo = true;
            continue;
          }
        }
      }

      // Estado 43: Identifica��o de simbolo alteracao seguido de numD ou numB
      // Estado 44: Identifica��o de nota adicionada seguida de alteracao com numB
      // Estado 46: Identifica��o de nota adicionada seguida de numB.
      // Precedencia: Estado 42
      if (estadoOrigem == 42 && !estadoFinal) {
        NotationalSymbol simbAlteracao = currentChordNotation.getAlterationSymbol(chordName,ponteiro);
        NotationalSymbol simbNotaAdicionada = currentChordNotation.getAddNoteSymbol(chordName,ponteiro);
        if (simbAlteracao != null) {
          ponteiro = simbAlteracao.getPosition(ponteiro);
          IntervalSymbol simbNumD = currentChordNotation.getSimboloIntervaloNumD(chordName,ponteiro);
          IntervalSymbol simbNumB = currentChordNotation.getSimboloIntervaloNumB(chordName,ponteiro);
	  estadoOrigem = 45;
          estadoFinal = false;
          //eh usado o metodo configuraAcordeDiminutoAlteracao por ser generico.
          //A chordName de entrada eh menor
          if (simbNumD != null) {
            if (!(cifraValida.containsInterval(IntervalFactory.getInterval(
                                                simbAlteracao.getUsedSymbol()
                                              + simbNumD.getUsedSymbol())))){
              cifraValida = configuraAcordeDiminutoAlteracao(cifraValida, simbAlteracao, simbNumD);
              // cifraValida.ordenaVetorIntervalo();
              ponteiro = simbNumD.getPosition(ponteiro);
	      reconheceuSimbolo = true;
	      continue;
            }else {
               reconheceuSimbolo = false;
               throw new ChordNotationException("Duplicated interval.", chordName);
             }
          }
          else if (simbNumB != null) {
            if (!(cifraValida.containsInterval(IntervalFactory.getInterval(
                                                simbAlteracao.getUsedSymbol()
                                              + simbNumB.getUsedSymbol())))){
              cifraValida = configuraAcordeDiminutoAlteracao(cifraValida, simbAlteracao, simbNumB);
              // cifraValida.ordenaVetorIntervalo();
              ponteiro = simbNumB.getPosition(ponteiro);
	      reconheceuSimbolo = true;
	      continue;
            }else {
               reconheceuSimbolo = false;
               throw new ChordNotationException("Duplicated interval.", chordName);
             }
          }
        }
        else if (simbNotaAdicionada != null) {
          ponteiro = simbNotaAdicionada.getPosition(ponteiro);
          simbAlteracao = currentChordNotation.getAlterationSymbol(chordName,ponteiro);
          IntervalSymbol simbNumB = currentChordNotation.getSimboloIntervaloNumB(chordName,ponteiro);
          if (simbAlteracao != null) {
            ponteiro = simbAlteracao.getPosition(ponteiro);
            simbNumB = currentChordNotation.getSimboloIntervaloNumB(chordName,ponteiro);
            //eh usado o metodo configuraAcordeDiminutoAlteracao por ser generico.
            //A chordName de entrada eh menor
            if (simbNumB != null) {
              if (!(cifraValida.containsInterval(IntervalFactory.getInterval(
                                                simbAlteracao.getUsedSymbol()
                                              + simbNumB.getUsedSymbol())))){
                cifraValida = configuraAcordeDiminutoAlteracao(cifraValida, simbAlteracao, simbNumB);
                // cifraValida.ordenaVetorIntervalo();
                ponteiro = simbNumB.getPosition(ponteiro);
                estadoOrigem = 45;
                estadoFinal = false;
	        reconheceuSimbolo = true;
	        continue;
              }else {
                 reconheceuSimbolo = false;
                 throw new ChordNotationException("Duplicated interval.", chordName);
               }
            }
          }else if (simbNumB != null) {
              ponteiro = simbNumB.getPosition(ponteiro);
              if (!(cifraValida.containsInterval(IntervalFactory.getInterval(
                                                simbNumB.getUsedSymbol())))){
                cifraValida = configuraAcordeAddNumB(cifraValida, simbNumB);
                // cifraValida.ordenaVetorIntervalo();
                estadoOrigem = 45;
                estadoFinal = false;
                reconheceuSimbolo = true;
                continue;
              }else {
                 reconheceuSimbolo = false;
                 throw new ChordNotationException("Duplicated interval.",chordName);
               }
          }
        }
      }

      // Estado 47: Identifica��o de simJuncaoIntervalo "^"
      // Estado 48: Identifica��o de Fim Alteracao ")"
      // Precedencia: Estado 45
      if (estadoOrigem == 45 && !estadoFinal) {
        NotationalSymbol simbFimAlteracao = currentChordNotation.getAlterationEndingSymbol(chordName,ponteiro);
        NotationalSymbol simbJuncaoIntervalo = currentChordNotation.getIntervalJunctionSymbol(chordName,ponteiro);
        if (simbFimAlteracao != null) {
          ponteiro = simbFimAlteracao.getPosition(ponteiro);
          reconheceuSimbolo = true;
	  //Adcionado por Leandro em 27/2/2005
	  estadoFinal = true;
	  estadoOrigem = 48;
          continue;
        }
        else if (simbJuncaoIntervalo != null) {
          ponteiro = simbJuncaoIntervalo.getPosition(ponteiro);
          estadoOrigem = 47;
          estadoFinal = false;
          reconheceuSimbolo = true;
          continue;
        }
      }

      // Estado 45: Identifica��o de nota adicionada seguido de numB
      // Estado 49: Identifica��o de nota adicionada seguido de altera��o + numB
      // Precedencia: Estado 47
      if (estadoOrigem == 47 && !estadoFinal) {
        NotationalSymbol simbNotaAdicionada = currentChordNotation.getAddNoteSymbol(chordName,ponteiro);
        if (simbNotaAdicionada != null) {
          ponteiro = simbNotaAdicionada.getPosition(ponteiro);
        }else {
          reconheceuSimbolo = false;
          continue;
        }
        IntervalSymbol simbNumB = currentChordNotation.getSimboloIntervaloNumB(chordName,ponteiro);
        NotationalSymbol simbAlteracao = currentChordNotation.getAlterationSymbol(chordName,ponteiro);
        if (simbNumB != null) {
          if (!(cifraValida.containsInterval(IntervalFactory.getInterval(
                                                simbNumB.getUsedSymbol())))){
            cifraValida = configuraAcordeAddNumB(cifraValida, simbNumB);
            // cifraValida.ordenaVetorIntervalo();
            ponteiro = simbNumB.getPosition(ponteiro);
            estadoOrigem = 45;
            estadoFinal = false;
            reconheceuSimbolo = true;
            continue;
          }else {
             reconheceuSimbolo = false;
             throw new ChordNotationException("Duplicated interval.",chordName);
           }
        }
        else if (simbAlteracao != null) {
          ponteiro = simbAlteracao.getPosition(ponteiro);
          simbNumB = currentChordNotation.getSimboloIntervaloNumB(chordName,ponteiro);
          if (simbNumB != null) {
            if (!(cifraValida.containsInterval(IntervalFactory.getInterval(
                                                simbAlteracao.getUsedSymbol()
                                              + simbNumB.getUsedSymbol())))){
              ponteiro = simbNumB.getPosition(ponteiro);
              //cifraValida = configuraAcordeAddNumB(cifraValida, simbNumB);
              // cifraValida.ordenaVetorIntervalo();
              //eh usado o metodo configuraAcordeDiminutoAlteracao por ser generico.
              //A chordName de entrada eh menor
              cifraValida = configuraAcordeDiminutoAlteracao(cifraValida, simbAlteracao, simbNumB);
              estadoOrigem = 45;
              estadoFinal = false;
              reconheceuSimbolo = true;
              continue;
            }else {
               reconheceuSimbolo = false;
               throw new ChordNotationException("Duplicated interval.",chordName);
             }
          }
        }
      }

      // Estado : Identifica��o do acorde menor com setima maior
      // Precedencia: Estado 50
      if (estadoOrigem == 50 && !estadoFinal) {
        NotationalSymbol simbInicioAlteracao = currentChordNotation.getAlterationBeginningSymbol(chordName,ponteiro);
        NotationalSymbol simbJuncaoIntervalo = currentChordNotation.getIntervalJunctionSymbol(chordName,ponteiro);
        if (simbInicioAlteracao != null) {
          ponteiro = simbInicioAlteracao.getPosition(ponteiro);
	  estadoOrigem = 53;
          estadoFinal = false;
        }else if (simbJuncaoIntervalo != null) {
           ponteiro = simbJuncaoIntervalo.getPosition(ponteiro);
           IntervalSymbol simbNumA = currentChordNotation.getSimboloIntervaloNumA(chordName,ponteiro);
           if (simbNumA != null) {
             cifraValida = configuraAcordeMenorNumANumB(cifraValida, simbNumA);
             // cifraValida.ordenaVetorIntervalo();
             ponteiro = simbNumA.getPosition(ponteiro);
	     estadoOrigem = 52;
             estadoFinal = true;
	     reconheceuSimbolo = true;
	     continue;
           }
         }
      }

      // Estado 52: Identificacao de juncao de intervalo seguido de numA OU inicio Alteracao
      // Precedencia: Estado 51
      if (estadoOrigem == 51){ // && !estadoFinal) {
        NotationalSymbol simbJuncaoIntervalo = currentChordNotation.getIntervalJunctionSymbol(chordName, ponteiro);
        NotationalSymbol simbInicioAlteracao = currentChordNotation.getAlterationBeginningSymbol(chordName,ponteiro);
        if (simbJuncaoIntervalo != null) {
          ponteiro = simbJuncaoIntervalo.getPosition(ponteiro);
          IntervalSymbol simbNumA = currentChordNotation.getSimboloIntervaloNumA(chordName, ponteiro);
          if (simbNumA != null) {
            cifraValida = configuraAcordeMenorNumANumB(cifraValida, simbNumA);
            // cifraValida.ordenaVetorIntervalo();
            ponteiro = simbNumA.getPosition(ponteiro);
	    estadoOrigem = 52;
            estadoFinal = true;
	    reconheceuSimbolo = true;
	    continue;
          }
        }else if (simbInicioAlteracao != null) {
           ponteiro = simbInicioAlteracao.getPosition(ponteiro);
	   estadoOrigem = 53;
           estadoFinal = false;
	   reconheceuSimbolo = true;
           continue;
        }
      }

      // Estado 53: Identificacao de inicio alteracao
      // Precedencia: Estado 52
      if (estadoOrigem == 52 && !estadoFinal) {
        NotationalSymbol simbInicioAlteracao = currentChordNotation.getAlterationBeginningSymbol(chordName, ponteiro);
        if (simbInicioAlteracao != null) {
          ponteiro = simbInicioAlteracao.getPosition(ponteiro);
	  estadoOrigem = 53;
          estadoFinal = false;
	  reconheceuSimbolo = true;
	  continue;
        }
      }

      // Estado 54, 55 ou 57: Identificacao de -->
      //                      Alteracao seguido de NumB ou seguido de NumD OU
      //                      Nona OU
      //                      Add seguido de 11 ou seguido de 13
      // Precedencia: Estado 53
      if (estadoOrigem == 53 && !estadoFinal) {
        NotationalSymbol simbAlteracao = currentChordNotation.getAlterationSymbol(chordName,ponteiro);
        IntervalSymbol simbIntervaloNona = currentChordNotation.getMajorNinthSymbol(chordName,ponteiro);
        NotationalSymbol simbNotaAdicionada = currentChordNotation.getAddNoteSymbol(chordName,ponteiro);
        if (simbAlteracao != null) {
          ponteiro = simbAlteracao.getPosition(ponteiro);
          IntervalSymbol simbNumB = currentChordNotation.getSimboloIntervaloNumB(chordName,ponteiro);
          IntervalSymbol simbNumD = currentChordNotation.getSimboloIntervaloNumD(chordName,ponteiro);
          if ((simbNumB != null) || (simbNumD != null)) {
            cifraValida = configuraAcordeDiminutoAlteracao(cifraValida, simbAlteracao, (simbNumB != null ? simbNumB : simbNumD));
            // cifraValida.ordenaVetorIntervalo();
            ponteiro = simbNumB != null ? simbNumB.getPosition(ponteiro)
                                        : simbNumD.getPosition(ponteiro);
	    estadoOrigem = 57;
            estadoFinal = false;
	    reconheceuSimbolo = true;
	    continue;
          }
        }else if (simbIntervaloNona != null) {
           cifraValida.addMajorNinth();
           ponteiro = simbIntervaloNona.getPosition(ponteiro);
	   estadoOrigem = 57;
           estadoFinal = false;
	   reconheceuSimbolo = true;
           continue;
         }else if (simbNotaAdicionada != null) {
            ponteiro = simbNotaAdicionada.getPosition(ponteiro);
	    estadoOrigem = 54;
            estadoFinal = false;
	    reconheceuSimbolo = true;
            continue;
        }
      }

      // Estado 56 ou 57: Identificacao de -->
      //                  Interval de 11 ou 13 OU
      //                  alteracao seguido de 11 ou 13
      // Precedencia: Estado 54
      if (estadoOrigem == 54 && !estadoFinal) {
        NotationalSymbol simbAlteracao = currentChordNotation.getAlterationSymbol(chordName,ponteiro);
        IntervalSymbol simbIntervaloDecimaPrimeiraJusta =
                       currentChordNotation.getPerfect11th(chordName,ponteiro);
        IntervalSymbol simbIntervaloDecimaTerceiraMaior =
                       currentChordNotation.getMajor13thSymbol(chordName,ponteiro);
        if (simbAlteracao != null) {
          ponteiro = simbAlteracao.getPosition(ponteiro);
          simbIntervaloDecimaPrimeiraJusta =
                         currentChordNotation.getPerfect11th(chordName,ponteiro);
          simbIntervaloDecimaTerceiraMaior =
                         currentChordNotation.getMajor13thSymbol(chordName,ponteiro);
          if ((simbIntervaloDecimaPrimeiraJusta != null) || (simbIntervaloDecimaTerceiraMaior != null)) {
            cifraValida = configuraAcordeDiminutoAlteracao(cifraValida, simbAlteracao,
                          (simbIntervaloDecimaPrimeiraJusta != null
                          ? simbIntervaloDecimaPrimeiraJusta : simbIntervaloDecimaTerceiraMaior));
            // cifraValida.ordenaVetorIntervalo();
            ponteiro = simbIntervaloDecimaPrimeiraJusta != null
                          ? simbIntervaloDecimaPrimeiraJusta.getPosition(ponteiro)
                          : simbIntervaloDecimaTerceiraMaior.getPosition(ponteiro);
	    estadoOrigem = 57;
            estadoFinal = false;
	    reconheceuSimbolo = true;
	    continue;
          }
        }else if ((simbIntervaloDecimaPrimeiraJusta != null) || (simbIntervaloDecimaTerceiraMaior != null)) {
           cifraValida = configuraAcordeAddNumB(cifraValida,
                         (simbIntervaloDecimaPrimeiraJusta != null
                         ? simbIntervaloDecimaPrimeiraJusta : simbIntervaloDecimaTerceiraMaior));
           // cifraValida.ordenaVetorIntervalo();
           ponteiro = simbIntervaloDecimaPrimeiraJusta != null
                         ? simbIntervaloDecimaPrimeiraJusta.getPosition(ponteiro)
                         : simbIntervaloDecimaTerceiraMaior.getPosition(ponteiro);
	   estadoOrigem = 57;
           estadoFinal = false;
	   reconheceuSimbolo = true;
	   continue;
         }
      }

      // Estado 58, 59, 60, 61: Identifica��o de -->
      //                        simbolo de Juncao Interval ("^") seguido de numB OU
      //                        simbolo de Juncao Interval ("^") seguido de nota adicionada OU
      //                        fimAlteracao OU
      //                        simbolo de Juncao Interval ("^") seguido de Alteracao mais numB
      // Precedencia: Estado 57
      if (estadoOrigem == 57 && !estadoFinal){
        NotationalSymbol simbFimAlteracao = currentChordNotation.getAlterationEndingSymbol(chordName, ponteiro);
        NotationalSymbol simbJuncaoIntervalo = currentChordNotation.getIntervalJunctionSymbol(chordName, ponteiro);
        if (simbFimAlteracao != null) {
          ponteiro = simbFimAlteracao.getPosition(ponteiro);
          estadoOrigem = 60;
          estadoFinal = ponteiro < chordName.length() && !(chordName.charAt(ponteiro)=='/') ? false : true;
	  reconheceuSimbolo = true;
	  continue;
        }else if (simbJuncaoIntervalo != null) {
           ponteiro = simbJuncaoIntervalo.getPosition(ponteiro);
           NotationalSymbol simbNotaAdicionada = currentChordNotation.getAddNoteSymbol(chordName, ponteiro);
           NotationalSymbol simbAlteracao = currentChordNotation.getAlterationSymbol(chordName, ponteiro);
           IntervalSymbol simbNumB = currentChordNotation.getSimboloIntervaloNumB(chordName, ponteiro);
           if (simbNotaAdicionada != null) {
             ponteiro = simbNotaAdicionada.getPosition(ponteiro);
             simbNumB = currentChordNotation.getSimboloIntervaloNumB(chordName, ponteiro);
             simbAlteracao = currentChordNotation.getAlterationSymbol(chordName, ponteiro);
             if (simbNumB != null) {
               if (!(cifraValida.containsInterval(IntervalFactory.getInterval(
                                                simbNumB.getUsedSymbol())))){
                cifraValida = configuraAcordeAddNumB(cifraValida, simbNumB);
                ponteiro = simbNumB.getPosition(ponteiro);
                // cifraValida.ordenaVetorIntervalo();
              }else {
                 reconheceuSimbolo = false;
                 throw new ChordNotationException(chordName + " Duplicated interval.",chordName);
               }
             }else if (simbAlteracao != null) {
                ponteiro = simbAlteracao.getPosition(ponteiro);
                simbNumB = currentChordNotation.getSimboloIntervaloNumB(chordName, ponteiro);
                if (simbNumB != null) {
                  if (!(cifraValida.containsInterval(IntervalFactory.getInterval(
                                                simbNumB.getUsedSymbol())))){
                    cifraValida = configuraAcordeDiminutoAlteracao(cifraValida, simbAlteracao, simbNumB);
                    ponteiro = simbNumB.getPosition(ponteiro);
                  }else {
                     reconheceuSimbolo = false;
                     throw new ChordNotationException(chordName + " Duplicated interval.",chordName);
                   }
                }else {
                   reconheceuSimbolo = false;
                   throw new ChordNotationException(chordName + " error ",chordName);
                 }
              }else {
                 reconheceuSimbolo = false;
                 throw new ChordNotationException(chordName + " invalida",chordName);
               }
             //estadoOrigem = 57;  //linha desnecessaria. Apenas para facilitar a manutencao.
	     //reconheceuSimbolo = true;
	     //continue;
           }else if (simbAlteracao != null){
              ponteiro = simbAlteracao.getPosition(ponteiro);
              simbNumB = currentChordNotation.getSimboloIntervaloNumB(chordName, ponteiro);
              if (simbNumB != null) {
                cifraValida = configuraAcordeDiminutoAlteracao(cifraValida, simbAlteracao, simbNumB);
                ponteiro = simbNumB.getPosition(ponteiro);
                //estadoOrigem = 57;  //linha desnecessaria. Apenas para facilitar a manutencao.
	        //reconheceuSimbolo = true;
	       // continue;
              }else {
                 reconheceuSimbolo = false;
                 throw new ChordNotationException(chordName + " error ",chordName);
               }

           }else if (simbNumB != null) {
              if (!(cifraValida.containsInterval(IntervalFactory.getInterval(
                                                simbNumB.getUsedSymbol())))){
                cifraValida = configuraAcordeAddNumB(cifraValida, simbNumB);
                ponteiro = simbNumB.getPosition(ponteiro);
                // cifraValida.ordenaVetorIntervalo();
              }else {
                 reconheceuSimbolo = false;
                 throw new ChordNotationException(chordName + " Duplicated interval.",chordName);
               }
           }else {
              reconheceuSimbolo = false;
              throw new ChordNotationException(chordName + " error ",chordName);
            }
           estadoOrigem = 57;  //linha desnecessaria. Apenas para facilitar a manutencao.
           estadoFinal = false;
	   reconheceuSimbolo = true;
	   continue;
         }
      }

      // Estado 5: Identifica��o do Acorde For�a
      // Precedencia: Estado 2
      if (estadoOrigem == 2){
	IntervalSymbol simbQuintaJusta = currentChordNotation.getPerfectFifthSymbol(chordName, ponteiro);
	if (simbQuintaJusta != null) {
	  cifraValida = configuraAcordeForca(cifraValida);
	  ponteiro = simbQuintaJusta.getPosition(ponteiro);
	  estadoOrigem = 5;
          estadoFinal = true;
	  reconheceuSimbolo = true;
	  continue;
	}else{reconheceuSimbolo = false;}
      }

      // Estado 62, 63, 64: Identifica��o do Acorde Forca seguido da quinta alterada
      // Precedencia: Estado 5
      if (estadoOrigem == 5){
	NotationalSymbol simbInicioAlteracao = currentChordNotation.getAlterationBeginningSymbol(chordName, ponteiro);
        if (simbInicioAlteracao != null) {
          ponteiro = simbInicioAlteracao.getPosition(ponteiro);
          NotationalSymbol simbAlteracao = currentChordNotation.getAlterationSymbol(chordName, ponteiro);
          if (simbAlteracao != null) {
            ponteiro = simbAlteracao.getPosition(ponteiro);
            String str5 = chordName.substring(ponteiro, (ponteiro+1));
            NotationalSymbol simbFimAlteracao = currentChordNotation.getAlterationEndingSymbol(chordName, ++ponteiro);
            if (!(str5.equals("5")) || (simbInicioAlteracao == null) || (simbAlteracao == null) || (simbFimAlteracao == null)) {
              reconheceuSimbolo = false;
            }else {
               cifraValida = configuraAcordeForca(cifraValida, simbAlteracao);
	       ponteiro = simbInicioAlteracao.getPosition(ponteiro);
	       estadoOrigem = 65;
               estadoFinal = true;
	       reconheceuSimbolo = true;
	       continue;
	     }
          }
        }
      }

      // Estado 6: Identifica��o do Acorde Suspenso seguido de um intervalo numC (2, 4, 9, 11)
      //  Precedencia: Estado 2
      if (estadoOrigem == 2){
	NotationalSymbol simbSuspensao = currentChordNotation.getSuspensionSymbol(chordName, ponteiro);
        //String aux = isInteiro(chordName.substring(ponteiro + 1));
        if (simbSuspensao != null) {
          ponteiro = simbSuspensao.getPosition(ponteiro);
          estadoOrigem = 6;
          estadoFinal = true;
          IntervalSymbol simbNumC = currentChordNotation.getSimboloIntervaloNumC(chordName,ponteiro);
          cifraValida = configuraAcordeSuspensao(cifraValida, simbNumC);
          ponteiro = simbNumC.getPosition(ponteiro);
          reconheceuSimbolo = true;
          continue;
	}else{
	  reconheceuSimbolo = false;
         // continue;
         // throw new InvalidChordException("chordName Inv�lida ", chordName);
	}
      }

      //Estado 66
      if (estadoFinal && (ponteiro < chordName.length())) {
      	NotationalSymbol simbInversao = currentChordNotation.getInvertionSymbol(chordName, ponteiro);
        if (simbInversao != null) {
          ponteiro = simbInversao.getPosition(ponteiro);
          NoteSymbol simbNota = currentChordNotation.getNoteSymbol(chordName, ponteiro);
          if (simbNota != null) {
            ponteiro = simbNota.getPosition(ponteiro);
       	    NotationalSymbol simbAlteracao = currentChordNotation.getAlterationSymbol(chordName, ponteiro);
            Note simbNotaBaixo;
            NoteSymbol simbBaixo;
            if (simbAlteracao != null) {
              ponteiro = simbAlteracao.getPosition(ponteiro);
              simbNotaBaixo = WesternMusicNotes.getNote(simbNota.getUsedSymbol()
                                   + simbAlteracao.getUsedSymbol());
              simbBaixo = new NoteSymbol(simbNotaBaixo.getSymbol(), simbNotaBaixo);
            }else if (ponteiro == (chordName.length())) {
               simbNotaBaixo = WesternMusicNotes.getNote(simbNota.getUsedSymbol());
               simbBaixo = new NoteSymbol(simbNotaBaixo.getSymbol(), simbNotaBaixo);
            }else{throw new ChordNotationException("Symbol not found: "+chordName + " Symbol: " + simbNota, chordName);}
            cifraValida = configuraAcordeInvertido(cifraValida, simbBaixo);
            //cifraValida.setBassNote(simbBaixo);
            reconheceuSimbolo = true;
            continue;
          }
        }
      }

      // Tratamento para simbolo n�o reconhecido
      // IMPORTANTE: DEIXAR SEMPRE POR �LTIMO;
      if (!reconheceuSimbolo){
	//System.out.println("Simbolo nao reconhecido");
        throw new ChordNotationException("Symbol not found: "+chordName + " Symbol: " + chordName.charAt(ponteiro), chordName);
	//ponteiro++; //Para sair do loop
        //break;
	//lan�ar erro
      }
    } //Wlihe
   return cifraValida;
  }

/*
 * Obt�m os intervalos para forma��o de uma Tr�ade Maior (acorde b�sico) e os
 * adiciona na rela��o de intervalos da cifraValida. Todos os demais acordes
 * s�o formados a partir da Tr�ade Maior.
 * @param acordeMaior Cifra j� validada pelo sistema que representa um acorde
 * maior.
 * @return Tr�ade Maior com os intervalos instanciados.
 */
  private ValidChordName configuraAcordeMaiorBasico(ValidChordName acordeMaior) {
    //Estatistica.contAcordeMaiorBasico++;
    acordeMaior.addRoot();
    acordeMaior.addMajorThird();
    acordeMaior.addPerfectFifth();
    return acordeMaior;
  }

/*
 * Obt�m os intervalos para forma��o de uma Tr�ade Menor e os adiciona na
 * rela��o de intervalos da cifraValida. Como o sistema j� possui os intervalos
 * de uma Tr�ade Maior, basta modificar o intervalo: Ter�a Maior --> Ter�a
 * Menor.
 * @param acordeMenor Cifra j� validada pelo sistema que representa um acorde
 * menor.
 * @return Tr�ade Menor com os intervalos instanciados.
 */
  private ValidChordName configuraAcordeMenorBasico(ValidChordName acordeMenor) {
    ////Estatistica.contAcordeMenorBasico++;
    acordeMenor.changeMajorThirdToMinor(1);
    return acordeMenor;
  }

/*
 * Obt�m os intervalos para forma��o de uma Tr�tade Diminuta e os adiciona na
 * rela��o de intervalos da cifraValida. Como o sistema j� possui os intervalos
 * de uma Tr�ade Maior, basta modificar os intervalos: Ter�a Maior --> Ter�a
 * Menor e Quinta Justa --> Quinta Diminuta e adicionar o intervalo S�tima
 * Diminuta.
 * @param acordeDiminuto Cifra j� validada pelo sistema que representa um acorde
 * diminuto.
 * @return T�trade Diminuta com os intervalos instanciados.
 */
  private ValidChordName configuraAcordeDiminuto(ValidChordName acordeDiminuto) {
    //Estatistica.contAcordeDiminuto++;
    acordeDiminuto.changeMajorThirdToMinor(1);
    acordeDiminuto.changePerfectFifthToDiminished(2);
    acordeDiminuto.addDiminishedSeventh();
    return acordeDiminuto;
  }

/*
 * Obt�m os intervalos para forma��o de um Acorde de For�a. Como o sistema j�
 * possui os intervalos de uma Tr�ade Maior, basta modificar o intervalo Ter�a
 * Maior --> Quinta Justa.
 * @param acordeForca Cifra j� validada pelo sistema que representa um acorde
 * de for�a.
 * @return Acorde de For�a com os intervalos instaciados.
 */
  private ValidChordName configuraAcordeForca(ValidChordName acordeForca) {
    //Estatistica.contAcordeForca1++;
    acordeForca.changeMajorThirdToPerfect12th(1);
    return acordeForca;
  }

/*
 * Obt�m os intervalos para forma��o de um Acorde de For�a quando este estiver
 * seguido de Altera��o (bemol ou sustenido).
 * @param acordeForca Cifra j� validada pelo sistema que representa um acorde
 * de for�a, seguido de altera��o.
 * @param simbAlt S�mbolo utilizado para representar a altera��o no acorde.
 * Exemplo: Bemol pode ser representado por "b".
 * @return Acorde de For�a com os intervalos instaciados.
 */
  private ValidChordName configuraAcordeForca(ValidChordName acordeForca, NotationalSymbol simbAlt) {
    //Estatistica.contAcordeForca2++;
    //acordeForca.addRoot();
    if (simbAlt.getSymbol().equals("#")) {
      acordeForca.changePerfec12thtToAugmented(1);
      acordeForca.changePerfectFifthToAugmented(2);
    } else if (simbAlt.getSymbol().equals("b")) {
      acordeForca.changePerfect12thToDiminished(1);
      acordeForca.changePerfectFifthToDiminished(2);
    } else acordeForca = null;
    return acordeForca;
  }

/*
 * Obt�m os intervalos para forma��o de um Acorde Diminuto quando este estiver
 * seguido de Altera��o (bemol ou sustenido) e um dos intervalos do conjunto
 * {2, 4, 5, 6, 9, 11, 13}.
 * @param acordeDiminutoAlteracao Cifra j� validada pelo sistema que representa
 * um acorde diminuto, seguido de altera��o e um dos intervalos do conjunto
 * {2, 4, 5, 6, 9, 11, 13}.
 * @param simbAlteracao S�mbolo utilizado para representar a altera��o no
 * acorde. Exemplo: Bemol pode ser representado por "b".
 * @param num Representa��o num�rica de um dos intervalos do conjunto
 * {2, 4, 5, 6, 9, 11, 13}.
 * @return Acorde Diminuto com os intervalos instaciados.
 */
  private ValidChordName configuraAcordeDiminutoAlteracao (ValidChordName acordeDiminutoAlteracao, NotationalSymbol simbAlteracao, IntervalSymbol num) {
    //Estatistica.contAcordeDiminutoAlteracao++;
    if (simbAlteracao.getSymbol().equals("#")) {
      if (num.getUsedSymbol().equals("2")) {
        acordeDiminutoAlteracao.addAugmentedSecond(1);}
      else if (num.getUsedSymbol().equals("4")) {
        acordeDiminutoAlteracao.addAugmentedFourth(2);}
      else if (num.getUsedSymbol().equals("6")) {
        acordeDiminutoAlteracao.addAugmentedSixth(3);}
      else if (num.getUsedSymbol().equals("5")) {
        acordeDiminutoAlteracao.changePerfectFifthToAugmented(2);}
      else if (num.getUsedSymbol().equals("9")) {
        acordeDiminutoAlteracao.addAugmentedNinth();}
      else if (num.getUsedSymbol().equals("11")) {
        acordeDiminutoAlteracao.addAugmented11th();}
      else if (num.getUsedSymbol().equals("13")) {
        acordeDiminutoAlteracao.addAugmented13th();}
    }
    if (simbAlteracao.getSymbol().equals("b")) {
      if (num.getUsedSymbol().equals("2")) {
        acordeDiminutoAlteracao.addMinorSecond(1);}
      else if (num.getUsedSymbol().equals("4")) {
        acordeDiminutoAlteracao.addDiminishedFourth(2);}
      else if (num.getUsedSymbol().equals("6")) {
        acordeDiminutoAlteracao.addMinorSixth(3);}
      else if (num.getUsedSymbol().equals("5")) {
        acordeDiminutoAlteracao.changePerfectFifthToDiminished(2);}
      else if (num.getUsedSymbol().equals("9")) {
        acordeDiminutoAlteracao.addMinorNinth();}
      else if (num.getUsedSymbol().equals("11")) {
        acordeDiminutoAlteracao.addDiminished11th();}
      else if (num.getUsedSymbol().equals("13")) {
        acordeDiminutoAlteracao.addMinor13th();}
    }
    return acordeDiminutoAlteracao;
  }

/*
 * Obt�m os intervalos para forma��o de um Acorde Diminuto quando este estiver
 * seguido de um dos intervalos do conjunto {9, 11, 13}.
 * @param acordeDiminutoNumB Cifra j� validada pelo sistema que representa
 * um acorde diminuto, seguido de um dos intervalos do conjunto {9, 11, 13}.
 * @param simbNumB Representa��o num�rica de um dos intervalos do conjunto
 * {9, 11, 13}.
 * @return Acorde Diminuto com os intervalos instaciados.
 */
  private ValidChordName configuraAcordeDiminutoNumB (ValidChordName acordeDiminutoNumB, IntervalSymbol simbNumB) {
    //Estatistica.contAcordeDiminutoNumB++;
    if (simbNumB.getUsedSymbol().equals("9")) {
      acordeDiminutoNumB.addMajorNinth();}
    else if (simbNumB.getUsedSymbol().equals("11")) {
      acordeDiminutoNumB.addPerfect11th();}
    else if (simbNumB.getUsedSymbol().equals("13")) {
      acordeDiminutoNumB.addMajor13th();}
    else {return null;}
    return acordeDiminutoNumB;
  }

/*
 * Obt�m os intervalos para forma��o de um Acorde com Suspens�o quando este estiver
 * seguido de um dos intervalos do conjunto {2, 4, 9, 11}.
 * @param acordeSuspensao Cifra j� validada pelo sistema que representa um acorde
 * com suspens�o, seguido de um dos intervalos do conjunto {2, 4, 9, 11}.
 * @param num Representa��o num�rica de um dos intervalos do conjunto
 * {9, 11, 13}.
 * @return Acorde com Suspens�o com os intervalos instaciados.
 */
  private ValidChordName configuraAcordeSuspensao (ValidChordName acordeSuspensao, IntervalSymbol num) {
    //Estatistica.contAcordeSuspensao++;
    if (num.getUsedSymbol().equals("2")||num.getUsedSymbol().equals("4")){
      int i;
      int tamVetIntervalos =  acordeSuspensao.getIntervals().length;
      for (i = 0; i < tamVetIntervalos; i++) {
        if (acordeSuspensao.getInterval(i).getNumericInterval() == 3) {
          acordeSuspensao.setInterval(i, num.getInterval());
          break;
        }else if (acordeSuspensao.getInterval(i).getNumericInterval()>3) {
           acordeSuspensao.addInterval(i, num);
           break;
        }
      }
    }
    else if (num.getUsedSymbol().equals("9")||num.getUsedSymbol().equals("11")){
      Interval vetIntevaloCifraValida[] = acordeSuspensao.getIntervals();
      int i;
      int tamVetIntervalos =  acordeSuspensao.getIntervals().length;
      // Busca a terca no vetor de intervalos da cifraValida
      for (i = 0; i < tamVetIntervalos; i++) {
        if (acordeSuspensao.getInterval(i).getNumericInterval() == 3) {
          acordeSuspensao.removeInterval(i);
          tamVetIntervalos--;
          if ((num.getUsedSymbol().equals("9"))&&(!(num.containsInterval(vetIntevaloCifraValida,"9")))) {
            i = tamVetIntervalos;
            break;
          }else if (!(num.containsInterval (vetIntevaloCifraValida,"11"))) {
             acordeSuspensao.addPerfect11th();
              break;
          }
        }
      }
      if (i == tamVetIntervalos) {
        boolean existeIntervalo11 = num.containsInterval (vetIntevaloCifraValida,"11");
        if ((num.getUsedSymbol().equals("11")) && !(existeIntervalo11)) {
           acordeSuspensao.addPerfect11th();
        }else if (num.getUsedSymbol().equals("9")
              &&!(num.containsInterval(vetIntevaloCifraValida,"9"))) {
           if (!(existeIntervalo11)) {
            acordeSuspensao.addMajorNinth();
           }else{
              for (i = 0; i < tamVetIntervalos; i++) {
                if (acordeSuspensao.getInterval(i).getNumericInterval() == 11) {
                  break;
                }
              }
              acordeSuspensao.addMajorNinth(i);
            }
        }
      }
    }
    return acordeSuspensao;
  }

/*
 * Obt�m os intervalos para forma��o de um Acorde Diminuto seguido do intervalo
 * S�tima Maior.
 * @param acordeSetimaMaior Cifra j� validada pelo sistema que representa um acorde
 * s�tima maior.
 * @param simbSetimaMaior Representa��o do intervalo s�tima maior na cifra.
 * @return Acorde S�tima Maior com os intervalos instaciados.
 */
  private ValidChordName configuraSetimaMaiorAcordeDiminuto(ValidChordName acordeSetimaMaior, IntervalSymbol simbSetimaMaior) {
    //Estatistica.contSetimaMaiorAcordeDiminuto++;
    acordeSetimaMaior.addMajorSeventh(3);
    return acordeSetimaMaior;
  }

/*
   * Obt�m os intervalos para forma��o de um Acorde Diminuto seguido de Note
   * Adicionada.
   * @param acordeDiminutoNotaAdicionada Cifra j� validada pelo sistema que
   * representa um acorde diminuto seguido de nota adicionada.
   * @param simbNum Representa��o num�rica do intervalo.
   * @return Acorde Diminuto seguido de Note Adicionada com os intervalos instaciados.
   */
  private ValidChordName configuraNotaAdicionadaAcordeDiminuto(ValidChordName acordeDiminutoNotaAdicionada, IntervalSymbol simbNum) {
    //Estatistica.contNotaAdicionadaAcordeDiminuto++;
    if (simbNum.getUsedSymbol().equals("11")) {
      acordeDiminutoNotaAdicionada.addPerfect11th();
    }else {
       acordeDiminutoNotaAdicionada.addMajor13th();
    }
    return acordeDiminutoNotaAdicionada;
  }

/*
 * Obt�m os intervalos para forma��o de um Acorde com S�tima Menor. Como o
 * sistema j� possui os intervalos de uma Tr�ade Maior, basta adicionar o intervalo:
 * S�tima Menor.
 * @param acordeSetimaMenor Cifra j� validada pelo sistema que representa um
 * acorde com s�tima menor.
 * @return Acorde S�tima Menor com os intervalos instaciados.
 */
  private ValidChordName configuraAcordeSetimaMenor (ValidChordName acordeSetimaMenor) {
    //Estatistica.contAcordeSetimaMenor++;
    acordeSetimaMenor.addMinorSeventh();
    return acordeSetimaMenor;
  }

/*
 * Obt�m os intervalos para forma��o de um Acorde com S�tima Maior. Como o sistema
 * j� possui os intervalos de uma Tr�ade Maior, basta adicionar o intervalo:
 * S�tima Maior.
 * @param acordeSetimaMaior Cifra j� validada pelo sistema que representa um
 * acorde com s�tima maior.
 * @return Acorde S�tima Maior com os intervalos instaciados.
 */
  private ValidChordName configuraAcordeSetimaMaior(ValidChordName acordeSetimaMaior) {
    //Estatistica.contAcordeSetimaMaior++;
    acordeSetimaMaior.addMajorSeventh();
    return acordeSetimaMaior;
  }

/*
 * Obt�m os intervalos para forma��o de um Acorde Menor quando este estiver
 * seguido de um dos intervalos do conjunto {2, 4, 5, 6}.
 * @param acordeMenorNumA Cifra j� validada pelo sistema que representa um
 * acorde menor seguido seguido de um dos intervalos do conjunto {2, 4, 5, 6}.
 * @param numA Representa��o num�rica de um dos intervalos do conjunto {2, 4, 5, 6}.
 * @return Acorde Menor com os intervalos intaciados.
 */
  private ValidChordName configuraAcordeMenorNumA(ValidChordName acordeMenorNumA, IntervalSymbol numA) {
    //Estatistica.contAcordeMenorNumA++;
    if (numA.getUsedSymbol().equals("2")) {
      acordeMenorNumA.addInterval(1,numA);
    }
    else if (numA.getUsedSymbol().equals("4")) {
      acordeMenorNumA.addInterval(2,numA);
    }
    else if (numA.getUsedSymbol().equals("6")) {
      acordeMenorNumA.addInterval(3,numA);
    }
    return acordeMenorNumA;
  }

/*
 * Obt�m os intervalos para forma��o de um Acorde Menor quando este estiver
 * seguido de um dos intervalos do conjunto NumA {2, 4, 5, 6} ou NumB {9, 11, 13}.
 * @param acordeMenorNumANumB Cifra j� validada pelo sistema que representa um
 * acorde menor seguido seguido de um dos intervalos do NumA {2, 4, 5, 6}
 * ou NumB {9, 11, 13}.
 * @param numB Representa��o num�rica de um dos intervalos do conjunto
 * NumA {2, 4, 5, 6} ou NumB {9, 11, 13}.
 * @return Acorde Menor com os intervalos instanciados.
 */
  private ValidChordName configuraAcordeMenorNumANumB(ValidChordName acordeMenorNumANumB, IntervalSymbol numB) {
    //Estatistica.contAcordeMenorNumANumB++;
    acordeMenorNumANumB.addInterval(-1,numB); //-1 significa adicionar no final do array de intervalos
    return acordeMenorNumANumB;
  }

/*
   * Obt�m os intevalos para forma��o de um Acorde com Note Adicionada quando este
   * estiver seguido de um dos intervalos do conjunto {9, 11, 13}.
   * @param acordeAddNumB Cifra j� validada pelo sistema que representa um
   * acorde com nota adicionada seguido de um dos intervalos do NumA {2, 4, 5, 6}
   * ou NumB {9, 11, 13}.
   * @param simbNumB do tipo <code>IntervalSymbol</code>
   * @return acordeAddNumB do tipo <code>ValidChordName</code>
   */
  private ValidChordName configuraAcordeAddNumB(ValidChordName acordeAddNumB, IntervalSymbol simbNumB) {
    if (simbNumB.getUsedSymbol() == "9") {
      acordeAddNumB.addMajorNinth();
    }
    else if (simbNumB.getUsedSymbol() == "11" ) {
      acordeAddNumB.addPerfect11th();
    }
    else if (simbNumB.getUsedSymbol() == "13" ) {
      acordeAddNumB.addMajor13th();
    }
    return acordeAddNumB;
  }

  private ValidChordName configuraAcordeInvertido(ValidChordName cifraValida, NoteSymbol simbBaixo) {
    cifraValida.setBassNote(simbBaixo);

    //Estatistica.contAcordeInvertido++;
/** @todo considerar se o baixo precisa de um intervalo associado */
    /*int distanciaFundamentalBaixo =
        NoteCollection.getDistanciaEntreNotas(cifraValida.getNotaFundamental().getNote(),
        simbBaixo.getNote(), false);
    Interval intervaloFundamentalBaixo = IntervalCollection.geraIntervalo(distanciaFundamentalBaixo);
    if (!(cifraValida.containsInterval(intervaloFundamentalBaixo))) {
      cifraValida.addInterval(intervaloFundamentalBaixo);
    }*/
    return cifraValida;
  }


}


