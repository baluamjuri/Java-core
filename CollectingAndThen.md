* List<RolesPermissionsEntity> rolesPermissionsEntities = 
 Map<String, Map<String, List<String>>> permissionsRegistry = rolesPermissionsEntities.stream()
    .collect(groupingBy(entity -> entity.getDomainAppMappingEntity().getAppCode(),
                        collectingAndThen(toList(), this::buildPermissionsRegistry)))
