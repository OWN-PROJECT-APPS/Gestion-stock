package com.bank.gestionstock.models;

public class Operation {

        private int num ;
        private int articleId;
        private String articleName;
        private int quantite;
        private String date;

//       For Select From Database
        public Operation(int num , String articleName,int articleId,int quantite, String date) {
            this.num  = num;
            this.articleName = articleName;
            this.date = date;
            this.quantite = quantite;
            this.articleId = articleId;
        }



    //        For Insert Into Database
        public Operation(int articleId, int quantite, String date) {
            this.articleId = articleId;
            this.quantite = quantite;
            this.date = date;
        }

        public Integer getNum() {
            return num;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getArticleId() {
        return articleId;
        }

        public void setArticleId(int articleId) {
        this.articleId = articleId;
        }

        public int getQuantite() {
        return quantite;
        }
        public String getArticleName() {
            return articleName;
        }

        public void setQuantite(int quantite) {
        this.quantite = quantite;
        }

        @Override
        public String toString() {
            return "Transaction => num = " +  num + " |  quantity = " + quantite + " |  date = " + date +  "\\";
        }
}
