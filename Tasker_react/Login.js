import React from 'react';
import { StyleSheet, View, TouchableHighlight, Text,  ScrollView,
  KeyboardAvoidingView, TextInput, TouchableOpacity,Dimensions ,
  DatePickerAndroid,Picker,Linking, AsyncStorage } from 'react-native';
  import {ref,firebaseAuth} from './Firebasedb';
  import {TaskArray} from './Taskarray';

  export class Login extends React.Component{
    static navigationOptions={
        title:'Log in'
      }
    constructor(props) {
      console.log('----------------Login--------------------:entered');
        super(props)
        this.state={
            email:'',
            password:'',
            error:''
        }
      }

      login(){
        that=this;
        console.log('----------------Login--------------------:login');
        firebaseAuth.signInWithEmailAndPassword(this.state.email, this.state.password)
          .then(()=>{
            var pers=ref.ref('Person');
            pers.once('value',(snapshot)=>{
              var perso=snapshot.val();
              for(var e in perso)
                if(e.address==firebaseAuth.email){
                  TaskArray.getInstance().p=e;
                  navigate('Home');
                }
                that.setState({error:'Profile not found.'});
            });
          }).catch(()=>{
            that.setState({error:'Incorect email or password.'});
          })
      }

      render(){
        const {navigate}=this.props.navigation;
        console.log('----------------Login--------------------:render');
          return(
              <View style={{flex:1}}>
            <TextInput style = {styles.input} 
            onChangeText={(value) => this.setState({email:value})} value={this.state.email}
            />
            <TextInput style = {styles.input}
                keyboardType='visible-password'
                onChangeText={(value) => this.setState({password:value})} value={this.state.password}
            />
            <TouchableHighlight style={styles.buttonContainer} 
                  onPress={()=>login()}>
                <Text  style={styles.buttonText}>LOGIN</Text>
            </TouchableHighlight>
            <Text style={{color:'red',fontSize:18}}>{this.state.error}</Text>
            </View>
           );
      }
  }

const styles = StyleSheet.create({
container: {
padding: 20
},
input:{
 height: 40,
 marginBottom: 10,
 padding: 10,
 color: '#fff'
},
buttonContainer:{
 backgroundColor: '#2980b6',
 paddingVertical: 15
},
buttonText:{
 color: '#fff',
 textAlign: 'center',
 fontWeight: '700'
}
});