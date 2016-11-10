/*
 * 所有程式碼皆於 JDK 6 環境，以Eclipse開發
 *
 * [History]
 * 20090731
 */
package hyweb.util;

import hyweb.core.kit.StreamKit;

import java.io.BufferedReader;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;

/**
 * @author Monyem
 * @version 1.0.091020.α
 * @since xbox 1.0
 */
public class CsvReader implements Readable{
        protected BufferedReader _bufReader;
        protected String[] _nextRecord;
        protected CharArrayWriter _charWriter;
        private int _fieldInRecord;//一行有幾筆
        private boolean _exact;//使用嚴謹的解析
        protected char _newLineChar = '\n';
        protected char _spChar = '"';
        protected char _splitChar = ',';

        /**
         * 使用最陽春的字串切割法
         * @param csvFile
         * @param enCoding  
         * @throws IOException
         */
        public CsvReader(File csvFile,String enCoding)throws IOException{
                this(StreamKit.getReader(csvFile, enCoding),0);
        }

        /**
         * 
         * @param csvFile
         * @param enCoding
         * @param filedsInRecord  設定一筆資料有幾個欄位，使用嚴謹的演算法，會較慢，但相容性極高。
         * @throws IOException
         */
        public CsvReader(File csvFile, String enCoding, int filedsInRecord)throws IOException{
                this(StreamKit.getReader(csvFile, enCoding),filedsInRecord);
        }

        /**
         * 建構子
         * @param csvReader
         * @param exactAlgorithm 是否使用嚴謹的演算法，會較慢，但相容性極高。
         */
        public CsvReader(Reader csvReader,int filedsInRecord){
                super();
                this._nextRecord = null;
                this._fieldInRecord = filedsInRecord;
                this._bufReader = new BufferedReader(csvReader);
                this._exact = filedsInRecord != 0;
                if(this._exact){
                        this._charWriter = new CharArrayWriter();
                }
        }

        /**
         * 關閉串流
         */
        public void close(){
                StreamKit.close(this._bufReader, this._charWriter);
        }

        /**
         * 讀取一行資料
         * @return
         * @throws
         */
        public String[] readRecord() throws IOException {
                return this._nextRecord;
        }

        /**
         * 是否還有下行資料，特殊字還原，我在考慮要不要包含在本功能內。或是寫個ｆｉｔｅｒ之類的公用介面
         * @see java.io.CharArrayWriter
         * @see java.io.BufferedReader
         */
        public boolean hasNext() throws IOException {
                if(this._exact){
                        this._nextRecord = this.readRecord(new String[this._fieldInRecord], 0, false);
                }else{
                        this._nextRecord = this._bufReader.readLine().split(",");
                }
                return this._nextRecord != null;
        }

        /**
         * 取得下筆資料
         * @param ret 回傳時用的字串陣列，因為本式可能會多次呼叫，所以才需要這兩個參數。
         * @param catchAmount 開始指標
         * @param isInSentence 目前是否在一串字裡面
         * @return ret指標
         * @throws IOException
         * 遇到"設定為啟動
         * 
         * 再遇到"
         * 如果下一個還是"表示不是結束
         * 
         * 如果非最後一個那",與,都是結尾
         * 如果是最後一個那"換行與換行都是結尾
         */
        private String[] readRecord(String[] ret, int catchAmount, boolean isInSentence) throws IOException{
                String line = this._bufReader.readLine();
                char[] lineArray = line.toCharArray();
                if(!isInSentence){
                        this._charWriter.reset();
                }
                for(int i = 0 ; i < lineArray.length ; i++){
                        if(isInSentence){
                                if(lineArray[i] == this._spChar){//遇上"切換模式
                                        int next = i + 1;
                                        if(next >= lineArray.length){//"是最後一個字
                                                isInSentence = false;
                                        }else{//非最後一個字
                                                if(lineArray[next] == this._spChar){//""
                                                        this._charWriter.append(this._spChar);
                                                        i = next;
                                                }else if(lineArray[next] == this._splitChar){//",
                                                        isInSentence = false;
                                                        i = next;
                                                        System.out.println(catchAmount);
                                                        ret[catchAmount ++] = this._charWriter.toString();
                                                        this._charWriter.reset();
                                                }
                                        }
                                }else{
                                        this._charWriter.append(lineArray[i]);
                                }
                        }else{
                                if(lineArray[i] == this._spChar){//遇上"切換模式
                                        int next = i + 1;
                                        int next2 = next + 1;
                                        if(next >= lineArray.length){//"是最後一個字
                                                isInSentence = true;
                                        }else{//非最後一個字
                                                if(lineArray[next] == this._spChar){//""
                                                        if(next2 >= lineArray.length){//"是倒數第二個字
                                                                if(lineArray[next2] == this._spChar){//是"""[enter]
                                                                        isInSentence = true;
                                                                        this._charWriter.append(this._spChar);
                                                                        i = next2;
                                                                }else{//""?[enter]
                                                                        //不可能有這種，這是錯誤
                                                                        return ret;
                                                                }
                                                        }else{//剩下超過兩個字
                                                                if(lineArray[next2] == this._spChar){//是"""?
                                                                        isInSentence = true;
                                                                        this._charWriter.append(this._spChar);
                                                                        i = next2;
                                                                }else if(lineArray[next2] == this._splitChar){//是"",?
                                                                        System.out.println(catchAmount);
                                                                        ret[catchAmount ++] = this._charWriter.toString();
                                                                        this._charWriter.reset();
                                                                        i = next2;
                                                                }else{//""??
                                                                        //不可能有這種，這是錯誤
                                                                        return ret;
                                                                }
                                                        }
                                                }else{//非""
                                                        isInSentence = true;
                                                }
                                        }
                                }else if(lineArray[i] == this._splitChar){
                                        System.out.println(catchAmount);
                                        ret[catchAmount ++] = this._charWriter.toString();
                                        this._charWriter.reset();
                                }else{
                                        this._charWriter.append(lineArray[i]);
                                }
                        }
                }
                if(!isInSentence && this._charWriter.size() != 0){
                        System.out.println(catchAmount);
                        ret[catchAmount ++] = this._charWriter.toString();
                        this._charWriter.reset();
                }
                if((++catchAmount) < this._fieldInRecord){
                        if(isInSentence){
                                this._charWriter.append(this._newLineChar);
                                this.readRecord(ret, catchAmount, isInSentence);
                        }
                        for( ; catchAmount < this._fieldInRecord ; catchAmount++){
                                ret[catchAmount] = "";
                        }
                }
                return ret;
        }

		@Override
		public int read(CharBuffer cb) throws IOException {
			return 0;
		}
}