export default class Person {
    constructor(id,name,address,role){
      this.name=name;
      this.address=address;
      this.id=id;
      this.role=role;
    }
    getTaskName(){
      return this.name;
    }
    getId(){
      return this.id;
    }
    getAddress(){
        return this.address;
    }
    getRole(){
        return this.role;
    }
  }