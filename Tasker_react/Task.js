export default class Task {
  constructor(name,description,idt,idm,idp,deadline){
    this.name=name;
    this.description=description;
    this.idt=idt;
    this.idm=idm;
    this.idp=idp;
    this.deadline=deadline;
  }
  getDeadline(){
    return this.deadline;
  }
  getTaskName(){
    return this.name;
  }
  getTaskDescription(){
    return this.description;
  }
  getIdT(){
    return this.idt;
  }
  getIdP(){
    return this.idp;
  }
  getIdM(){
    return this.idm;
  }
}