FROM airhacks/glassfish
COPY ./target/car-management.war ${DEPLOYMENT_DIR}
