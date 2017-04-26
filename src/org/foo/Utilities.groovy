/**
 * Created by d049997 on 26/04/2017.
 */

package org.foo

class Utilities implements Serializable {
    def steps
    Utilities(steps){
        this.steps = steps
    }

    def foo(){
        if (steps.isUnix()) {
            steps.sh "echo first"
            steps.sh "echo two"
        }
        else{ 
            steps.bat "echo first"
            steps.bat "echo two"
        }
    }

}
