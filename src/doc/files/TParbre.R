library(readxl)
#changement de répertoire
setwd("C:/Users/esprit/Desktop/amal/2017-2018/datamining/data mining")
#chargement des données dans la première feuille de calcul
#les données sont dans la première feuille
breast.train <- read_excel("breast.xls", sheet = 1, col_names = TRUE, col_types = NULL, na = "", skip = 0)
breast.test <- read_excel("breast.xls", sheet = 2, col_names = TRUE, col_types = NULL, na = "", skip = 0)
library(rpart)
breast.arbr<-rpart( classe ~ ., data = breast.train,method = "class")
print(breast.arbr)
plot(breast.arbr)
text(breast.arbr)
#type - Prédiction est une variable qualitative (classement)
predict.class <-predict(breast.arbr,newdata=breast.test ,type="class")
print(predict.class)
print(summary(predict.class))
#matrice de confusion table(.) construit un tableau croisé entre la cible observée (classe) et la prédiction du modèle (pred.classe)
mc <- table(breast.test$classe,predict.class)
print(mc)
erreur <-(mc[1,2]+mc[2,1])/sum(mc)
print(erreur)

