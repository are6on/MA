
import { AsyncStorage } from 'react-native';
import Task from './Task';

export default class Taskarray {
    
        static myInstance = null;
        static p=null;
        
        static getInstance() {
            if (this.myInstance == null) {
                this.myInstance = new Taskarray();
            }
    
            return this.myInstance;
        }
    }