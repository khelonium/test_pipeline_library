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
        steps.bat "echo first"
        steps.bat "echo two"
    }

}