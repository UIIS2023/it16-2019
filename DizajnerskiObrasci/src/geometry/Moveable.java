package geometry;

public interface Moveable {
	// interfejsi su POTPUNO APSTRAKTNE klase -> svaka metoda u okviru interfejsa je
	// apstraktna
	// ne mozemo da instanciramo interfejs; interfejsi se ne nasledjuju, oni se implementiraju

	public abstract void moveBy(int byX, int byY);
	// void moveBy(int byX,int byY);


}
