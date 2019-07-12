package com.corsair.sparrow.pirate.conf.thread;

import com.corsair.sparrow.pirate.conf.domain.dto.UserDTO;

/**
 * @author jack
 */
public class UserContext {


    public static final InheritableThreadLocal<UserDTO> USER_THREAD = new InheritableThreadLocalMap<>();

    public static final class InheritableThreadLocalMap<T extends UserDTO> extends InheritableThreadLocal<UserDTO> {

        @Override
        protected UserDTO initialValue() {
            return super.initialValue();
        }

        @Override
        protected UserDTO childValue(UserDTO parentValue) {
            if( parentValue != null){
                return super.childValue(parentValue);
            }
            return null;
        }
    }

    /**
     * 获取当前UserContext值
     * @return
     */
    public static UserDTO getUserDTO(){
        return UserContext.USER_THREAD.get();
    }

    /**
     * 设置当前UserContext值
     * @param userDTO
     */
    public static void setUserDTO(UserDTO userDTO){
        UserContext.USER_THREAD.set(userDTO);
    }

}
