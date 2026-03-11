@SpringBootApplication
│
├── Application Entry Point
│ ├── @SpringBootApplication
│ │ ├── @ComponentScan
│ │ ├── @EnableAutoConfiguration
│ │ └── @Configuration
│ │
│ └── @ComponentScan
│
├── Configuration Layer
│ ├── @Configuration ✅
│ │ ├── @Bean ✅
│ │ ├── @ConfigurationProperties ✅
│ │ ├── @PropertySource ✅
│ │ ├── @EnableConfigurationProperties ✅
│ │ └── @EnableJpaRepositories ✅ (NEW!)
│ │
│ └── @EnableAutoConfiguration ✅
│ └── Auto-configuration based on classpath
│
├── Component Layer
│ ├── @Component
│ ├── @Service
│ │ └── @Transactional ✅
│ ├── @Repository
│ │ ├── @Query ✅
│ │ ├── @Modifying ✅
│ │ ├── @Returning ✅
│ │ └── @NativeQuery ✅
│ ├── @Entity
│ │ ├── @Table
│ │ ├── @Column
│ │ ├── @Id
│ │ ├── @UniqueConstraint ✅
│ │ ├── @OneToMany ✅
│ │ ├── @ManyToOne ✅
│ │ ├── @ManyToMany ✅
│ │ └── @GeneratedValue
│
├── Controller Layer
│ ├── @RestController
│ ├── @Controller ✅
│ ├── @RequestMapping ✅
│ ├── @GetMapping ✅
│ ├── @PostMapping ✅
│ ├── @PutMapping ✅
│ ├── @PatchMapping ✅
│ ├── @DeleteMapping ✅
│ ├── @ResponseStatus ✅
│ ├── @Valid ✅
│ └── @RequestParam ✅
│ ├── @PathVariable ✅
│ ├── @RequestHeader ✅
│ ├── @CookieValue ✅
│ └── @Header ✅
│
├── Validation Layer
│ ├── @Valid ✅
│ ├── @Validated ✅
│ ├── @NotNull ✅
│ ├── @NotNullOrEmpty ✅
│ ├── @NotBlank ✅ (NEW!)
│ ├── @Size ✅
│ ├── @Min ✅
│ ├── @Max ✅
│ ├── @Pattern ✅
│ ├── @Email ✅
│ ├── @Url ✅ (NEW!)
│ ├── @Decimal ✅
│ ├── @Future ✅
│ ├── @Past ✅
│ ├── @AssertTrue ✅ (NEW!)
│ ├── @AssertFalse ✅ (NEW!)
│ ├── @Positive ✅ (NEW!)
│ └── @Negative ✅ (NEW!)
│
├── Async & Scheduling (NEW!)
│ ├── @Async ✅
│ ├── @Scheduled ✅
│ └── @AsyncConfiguration ✅
│
├── Security Layer (NEW!)
│ ├── @EnableGlobalMethodSecurity ✅
│ ├── @PreAuthorize ✅ (NEW!)
│ ├── @PostAuthorize ✅ (NEW!)
│ ├── @PreFilter ✅ (NEW!)
│ ├── @PostFilter ✅ (NEW!)
│ ├── @DenyAll ✅ (NEW!)
│ └── @AllowAll ✅ (NEW!)
│
└── Messaging Layer (NEW!)
├── @SendTo ✅
├── @Message ✅
└── @MessageDriven ✅ (NEW!)
