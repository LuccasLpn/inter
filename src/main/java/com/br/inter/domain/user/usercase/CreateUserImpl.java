    package com.br.inter.domain.user.usercase;

    import com.br.inter.application.request.CreateUserRequest;
    import com.br.inter.domain.user.gateway.CreateUserGateway;
    import com.br.inter.domain.user.model.User;

    public class CreateUserImpl implements CreateUser {

        private final CreateUserGateway createUserGateway;


        public CreateUserImpl(CreateUserGateway createUserGateway) {
            this.createUserGateway = createUserGateway;
        }

        public User execute(CreateUserRequest userRequest) {
            return createUserGateway.save(new User(
                    userRequest.getName(),
                    userRequest.getEmail(),
                    userRequest.getPassword(),
                    userRequest.getDocument()
            ));
        }
    }
