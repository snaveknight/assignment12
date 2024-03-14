For this assignment I have seen an anti patterns in the TextProcessing class. The use of a static initialization block to populate 
lemmatizationMap and stopWords can make the class harder to test and extend. 
This approach tightly couples data initialization with class loading, which might not be desirable in all cases.

I will remove the static blocks. 
