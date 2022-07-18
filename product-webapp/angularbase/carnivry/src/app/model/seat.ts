export class Seat {
    public row: number;
    public colm: number;
    public seatId: number;
    public seatPrice: number;

    constructor(row:number,colm:number, seatPrice:number){
        this.row=row;
        this.colm=colm;
        this.seatPrice=seatPrice;
    }
}
