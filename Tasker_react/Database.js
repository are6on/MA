import React from 'react';
import { StyleSheet, Text, View, TouchableHighlight } from 'react-native';
import Task from './Task'
import Realm from 'realm'

    const TaskSchema = {
        name: 'Task',
        primaryKey: 'idt',
        properties: {
            idt:'int',
            name:'string',
            description:'string',
            idp:'int',
            idm:'int',
            deadline:'date',
        }
      };
      const PersonSchema = {
        name: 'Person',
        primaryKey: 'id',
        properties: {
          id:'int',
          name:'string',
          address:'string',
          role:'int'
        }
      };
      

export default class DB {
        realm=new Realm({schema:[PersonSchema,TaskSchema]});
        constructor(){}

        getnewidp()
        {
            var id=0;
            var r=this.realm;
            let p=r.objects('Person').sorted('id', true);
            if(p.length>0)
                id = p[0].id+1;
            return id;
        }

        getnewidt()
        {
            var id=0;
            var r=this.realm;
            let p=r.objects('Task').sorted('idt', true);
            if(p.length>0)
                id = p[0].idt+1;
            return id;
        }

        addPerson(obj){
            var r=this.realm;
                while(1){
                    try{
                        i=this.getnewidp();
                        r.write(()=>{
                            r.create('Person',{
                               id:i,
                               name:obj.name,
                               address:obj.address,
                               role:obj.role
                            }); 
                        })
                        break;
                    }
                    catch(e){
                    }
                }
            }
        getPerson(id){
            var r=this.realm;
            var list=null;
            
                    var l=r.objects('Person')
                    .filtered('id = '+id.toString());
                    if(l.length>0)
                        list=l[0];
            return list;
        }
        getPersons(){
            var r=this.realm;
            var list=new Array();
                    var p=r.objects('Person')
                    .filtered('role = 1');
                    if(p.length)
                        list=p;
                    
        
            return list;
        }
        
        addTask(obj){
            var r=this.realm;
                    while(1){
                        try{
                            i=this.getnewidt();
                            r.write(()=>{
                                r.create('Task',{
                                    idt:i,
                                    name:obj.getTaskName(),
                                    description:obj.getTaskDescription(),
                                    idp:obj.getIdP(),
                                    idm:obj.getIdM(),
                                    deadline:obj.getDeadline()
                                }) 
                            })
                            break;
                        }
                        catch(e){
                            //try again
                        }
                    }
            }
        removeTask(id){
            var r=this.realm;
           
                    r.write(() => {
                    let list=r.objects('Task')
                    .filtered('idt = '+id.toString());
                    if(list.length > 0)
                        r.delete(list[0]);
                    })
                   
        }
        updateTask(obj){
            var r=this.realm;
            var task=this.getTask(obj.getIdT());
                    r.write(() => {
                            task.name=obj.getTaskName();
                            task.description=obj.getTaskDescription();
                            task.idp=obj.getIdP();
                            task.idm=obj.getIdM();
                            task.deadline=obj.getDeadline();
                        })
                
            
        }
        getTask(id){
            var r=this.realm;
            task=null;
            
                    let list=r.objects('Task')
                    .filtered('idt = '+id.toString());
                    if(list.length >0)
                    task=list[0];
                    
            return task;
        }
        getTasks(){
            var r=this.realm;
            tasks=null;
            let list=r.objects('Task').filtered('idm = 0');
                    if(list.length >0)
                        tasks=list;
                    
            return tasks;
        }

        getNrOfTasks(id){
            var r=this.realm;
            let list=r.objects('Task').filtered('idp = '+id.toString());    
            return list.length;
        }
    }

    export const DaBe=new DB();